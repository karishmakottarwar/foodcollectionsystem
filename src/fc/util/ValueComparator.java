package fc.util;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator {
	Map baseMap;
	
	public ValueComparator(Map baseMap){
		this.baseMap = baseMap;
	}

	public int compare(Object a, Object b) {
		if ((Integer)baseMap.get(a) <= (Integer)baseMap.get(b)) {
            return -1;
        } else {
            return 1;
        } 
	}

}
