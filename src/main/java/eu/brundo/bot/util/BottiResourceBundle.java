package eu.brundo.bot.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class BottiResourceBundle {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("bot", Locale.GERMAN);

    public static String getMessage(final String messageKey, final Object... values) {
        return MessageFormat.format(Optional.ofNullable(resourceBundle.getString(messageKey)).orElse("{" + messageKey + "}"), values);
    }
}
