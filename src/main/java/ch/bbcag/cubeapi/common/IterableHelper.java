package ch.bbcag.cubeapi.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class IterableHelper<T> {

    private Iterable<T> stored = new HashSet<>();

    private boolean isClear = true;

    public int sizeOfIterable(Iterable<T> i) {
        int count = 0;
        for (T t : i) {
            count++;
        }
        return count;
    }

    public Iterable<T> intersectionOfTwoIterables(Iterable<T> i1, Iterable<T> i2) {
        List<T> l1 = iterableToList(i1);
        List<T> l2 = iterableToList(i2);
        // Gets the intersection of two lists
        return intersectionOfTwoLists(l1, l2);
    }

    public void clearStored() {
        List<T> l = iterableToList(stored);
        l.clear();
        stored = l;
        isClear = true;
    }

    public void addIterableToIntersectionOfStored(Iterable<T> ts) {
        if (!isClear) stored = intersectionOfTwoIterables(stored, ts);
        else {
            stored = ts;
            isClear = false;
        }
    }

    public Iterable<T> getStored() {
        return stored;
    }

    private Set<T> intersectionOfTwoLists(List<T> l1, List<T> l2) {
        if (l1.isEmpty()) return new HashSet<T>(l1);
        if (l2.isEmpty()) return new HashSet<T>(l2);
        return l1.stream()
                .distinct()
                .filter(l2::contains)
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