package ch.bbcag.cubeapi.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class IterableHelper<T> {

    public Iterable<T> intersectionOfTwoIterables(Iterable<T> i1, Iterable<T> i2) {
        List<T> l1 = iterableToList(i1);
        List<T> l2 = iterableToList(i2);
        // Gets the intersection of two lists
        return l1.stream().distinct().filter(l2::contains).collect(Collectors.toSet());
    }

    public Iterable<T> intersectionOfThreeIterables(Iterable<T> i1, Iterable<T> i2, Iterable<T> i3) {
        List<T> l1 = iterableToList(i1);
        List<T> l2 = iterableToList(i2);
        List<T> l3 = iterableToList(i3);
        // Intersect the first two Lists
        Set<T> firstTs = l1.stream()
                .distinct()
                .filter(l2::contains)
                .collect(Collectors.toSet());
        // Intersect the new list with the 3rd list
        return firstTs.stream()
                .distinct()
                .filter(l3::contains)
                .collect(Collectors.toSet());
    }

    private List<T> iterableToList(Iterable<T> ts) {
        List<T> listCubers = new ArrayList<>();
        for (T t : ts) {
            listCubers.add(t);
        }
        return listCubers;
    }
}