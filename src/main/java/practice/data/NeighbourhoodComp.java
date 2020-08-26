package practice.data;

import java.util.Comparator;

public class NeighbourhoodComp implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}
