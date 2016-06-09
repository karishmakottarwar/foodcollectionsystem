import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import fc.util.FCSUtil;
import fc.util.ValueComparator;


public class MainClass {
	public static void main(String args[]){
		Map<String, Integer> wg = new HashMap();
		wg.put("Shrikant", 10);
		wg.put("shri1",20);
		/*wg.put("a", 50);
		wg.put("b", 65);
		wg.put("c", 98);*/
		Map<String, Integer> og = new HashMap();
		og.put("p1", 65);
		og.put("p2", 25);
		og.put("p3", 21);
		og.put("p4", 98);
		FCSUtil fcsUtil= new FCSUtil();
		System.out.println("These users are allocated "+fcsUtil.resourceAllocation("15",wg, og));
		
	}
}
