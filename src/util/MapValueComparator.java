package util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapValueComparator implements Comparator<Map.Entry<UserItemPair, Double>> {

    @Override
    public int compare(Entry<UserItemPair, Double> me1, Entry<UserItemPair, Double> me2) {

        return me2.getValue().compareTo(me1.getValue());
    }
}
