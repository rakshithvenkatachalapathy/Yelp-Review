package practice.data;

import java.util.Comparator;

public class BusinessComp implements Comparator<BusinessDetails> {
    @Override
    public int compare(BusinessDetails o1, BusinessDetails o2) {

        return o2.getName().compareTo(o1.getName());
    }
}
