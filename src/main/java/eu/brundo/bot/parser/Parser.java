package eu.brundo.bot.parser;

import eu.brundo.bot.commands.AbstractCommand;
import eu.brundo.bot.commands.CommandCategories;
import eu.brundo.bot.services.AchievementService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parser {

    private final AchievementService achievementService;

    public Parser(final AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    public AbstractCommand parse(final Path filePath) throws IOException {
        final List<String> lines = Files.readAllLines(filePath);

        final List<Integer> seperatorIndices = IntStream.range(0, lines.size())
                .filter(index -> lines.get(index).trim().startsWith("----------------"))
                .mapToObj(index -> index)
                .collect(Collectors.toList());

        final List<List<String>> sections = new ArrayList<>();

        int currentIndex = 0;
        for (final int sepIndex : seperatorIndices) {
            if (currentIndex < lines.size()) {
                final List<String> commandList = lines.subList(currentIndex, sepIndex);
                sections.add(new ArrayList<>(commandList));
            }
            currentIndex = sepIndex + 1;
        }


        if (!sections.isEmpty()) {
            final List<String> firstSection = sections.remove(0);

            if (firstSection.isEmpty()) {
                throw new RuntimeException("Command does not contain any metadata");
            }
            if (!firstSection.remove(0).trim().equals("[METADATA]")) {
                throw new RuntimeException("Command does not start with metadata section");
            }

            //TODO: Define metadata
            final String commandName = firstSection.stream()
                    .filter(line -> line.trim().startsWith("name="))
                    .map(line -> line.trim().substring("name=".length()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("No name for command defined"));
            final String commandHelpText = firstSection.stream()
                    .filter(line -> line.trim().startsWith("help="))
                    .map(line -> line.trim().substring("help=".length()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("No name for command defined"));
            final boolean listInHelp = firstSection.stream()
                    .filter(line -> line.trim().startsWith("showInHelp="))
                    .map(line -> line.trim().substring("showInHelp=".length()))
                    .filter(value -> value.toLowerCase().equals("true"))
                    .findAny()
                    .isPresent();
            final boolean onlyForAdmin = firstSection.stream()
                    .filter(line -> line.trim().startsWith("onlyForAdmin="))
                    .map(line -> line.trim().substring("onlyForAdmin=".length()))
                    .filter(value -> value.toLowerCase().equals("true"))
                    .findAny()
                    .isPresent();

            if (!sections.isEmpty()) {
                final List<CommandCase> cases = sections.stream()
                        .map(sectionLines -> new CommandCase(sectionLines, achievementService))
                        .collect(Collectors.toList());

                return new AbstractCommand(commandName) {
                    @Override
                    protected void onCommand(final MessageReceivedEvent event) {
                        cases.stream().filter(commandCase -> commandCase.matches(event))
                                .findFirst()
                                .ifPresent(commandCase -> commandCase.execute(event));
                    }

                    @Override
                    public CommandCategories getCategory() {
                        return CommandCategories.ADDITIONAL_CATEGORY;
                    }

                    @Override
                    public String getHelp() {
                        return commandHelpText;
                    }

                    @Override
                    public boolean listInHelp() {
                        return listInHelp;
                    }

                    @Override
                    public boolean isAllowed(final Member overviewRequester, final MessageChannel channel) {
                        if (onlyForAdmin) {
                            return isAdmin(overviewRequester);
                        }
                        return true;
                    }
                };

            } else {
                throw new RuntimeException("Command does not contain any cases");
            }
        } else {
            throw new RuntimeException("Command does not contain any sections");
        }

    }
}
