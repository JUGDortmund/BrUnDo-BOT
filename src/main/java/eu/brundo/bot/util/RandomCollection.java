package eu.brundo.bot.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class RandomCollection<E> {

    private final Collection<E> elements;

    private final Set<E> usedElements;

    public RandomCollection(final Collection<E> elements) {
        this.usedElements = new CopyOnWriteArraySet<>();
        this.elements = Collections.unmodifiableCollection(elements);
        if (elements.isEmpty()) {
            throw new IllegalStateException("Collection should not be empty");
        }
    }

    public E get() {
        final List<E> possibleElements = elements.stream()
                .filter(element -> !usedElements.contains(element))
                .collect(Collectors.toList());
        if (possibleElements.isEmpty()) {
            usedElements.clear();
            possibleElements.addAll(elements);
        }
        Collections.shuffle(possibleElements);
        final E element = possibleElements.get(0);
        usedElements.add(element);
        return element;
    }
}
