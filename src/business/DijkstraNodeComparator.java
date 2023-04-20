package business;

import java.util.Comparator;

public class DijkstraNodeComparator implements Comparator {


    @Override
    public int compare(Object o1, Object o2) {
        Node one = (Node) o1;
        Node two = (Node) o2;
        if(one.getDjikstraWeight() < two.getDjikstraWeight()){
            return -1;
        } else if (one.getDjikstraWeight() > two.getDjikstraWeight()) {
            return 1;
        } else {
            return 0;
        }
    }
}
