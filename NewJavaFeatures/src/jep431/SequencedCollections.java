package jep431;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SequencedCollections {
    public static void main(String[] args) {
        List<Integer> wrongList = Arrays.asList(2, 5, 7, 11, 3, 4, 99, 11);
        ArrayList<Integer> list = Stream.of(2, 5, 7, 11, 3, 4, 99, 11)
                .collect(Collectors.toCollection(ArrayList::new));
        try {
            wrongList.addFirst(100);
        } catch (UnsupportedOperationException e) {
            System.out.println("Arrays.asList() method does not guarantee to return" +
                    " implementation of List that is actually sorted");
        }
        System.out.println("Original list: " + list);
        list.addFirst(100);
        list.removeLast();
        List<Integer> changedList = list.reversed();
        System.out.println("Changed list: " + changedList);

        TreeSet<Integer> treeSet = Stream.of(1, 24, 131, 25, 14, 1, 88, 1234, 15)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingInt(i -> i))));

        try {
            treeSet.addFirst(43);
        } catch (UnsupportedOperationException e) {
            System.out.println("TreeSet uvijek mora biti sortiran pa ne dozvoljava addFirst metodu");
        }

        Set<Integer> reversedSet = treeSet.reversed();
//        reversedSet.addFirst(55);

        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>(treeSet);
        linkedHashSet.addFirst(10);

        System.out.println("Sorted set: " + treeSet);
        System.out.println("Reversed sorted set: " + reversedSet);
        System.out.println("Linked hash set: " + linkedHashSet);
    }
}
