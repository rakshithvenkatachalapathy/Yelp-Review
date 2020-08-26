package practice.data;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class NameComp implements Comparator<String> {
    Map<String, String> base;

    public NameComp(Map<String, String> base) {
        this.base = base;
    }

    @Override
    public int compare(String o1, String o2) {
        if (base.get(o1).compareTo(base.get(o2)) == 0) {
            return o1.compareTo(o2);
        } else
            return base.get(o1).compareTo(base.get(o2));
    }

}
