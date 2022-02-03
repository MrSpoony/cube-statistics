package ch.bbcag.cubeapi.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class IterableHelper<T> {

    public Iterable<T> intersectionOfIterables(Iterable<T> i1, Iterable<T> i2) {
        List<T> l1 = iterableToList(i1);
        List<T> l2 = iterableToList(i2);
        // Gets the intersection of two lists
        return l1.stream().distinct().filter(l2::contains).collect(Collectors.toSet());
    }

    private List<T> iterableToList(Iterable<T> ts) {
        List<T> listCubers = new ArrayList<>();
        for (T t: ts) {
            listCubers.add(t);
        }
        return listCubers;
    }
}