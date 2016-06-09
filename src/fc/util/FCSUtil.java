package fc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.constant.FCSConstants;

import fcs.bean.ResourceAllocationBean;

/**
 * @author PRASHANT
 * 
 */
public class FCSUtil {
	/*
	 * Output: status and ResourcesAllocated Info : This method analyse the
	 * requested quantity and allocate the resource to it
	 * Inputs : 
	 * 1. requestedQuentityParam : It is the food quantity which is to be collected by Collector.
	 * 2. resWithinGeo : The key-values pair of collectore which are inside the given geo fence 
	 * 3. resOutsideGeo : The key-value pair of collectors which are outside the geo fence
	 * 
	 * output :
	 * 1. status : this states if the collector is allocated or not
	 * 2. allocatedResources : The collector which are allocated for collection 
	 */
	public ResourceAllocationBean resourceAllocation(String requestedQuentityParam, Map<String, Integer> resWithinGeo, Map<String, Integer> resOutsideGeo) {
		try {
			String status = FCSConstants.ALLOCATION_STATUS_NA;
			List<String> allocatedResources = new ArrayList<String>();
			Integer requestedQuentity = Integer.parseInt(requestedQuentityParam);   

			/* Sorted Within Geo resources */
			ValueComparator bvcWithinGeo = new ValueComparator(resWithinGeo);
			TreeMap<String, Integer> sorted_mapWithinGeo = new TreeMap(bvcWithinGeo);   //This will sort the within geo fence values , for more information read TreeMap of Java Collection
			sorted_mapWithinGeo.putAll(resWithinGeo);

			/* Sorted outside Geo resources */
			ValueComparator bvcOutsideGeo = new ValueComparator(resOutsideGeo);
			TreeMap<String, Integer> sorted_mapOutsideGeo = new TreeMap(bvcOutsideGeo);  //This will sort the outside geo fance values
			sorted_mapOutsideGeo.putAll(resOutsideGeo);
			int totalWGcapacity = 0;													//to hold Total capacity of all the collector within the geo fance

			for (Map.Entry<String, Integer> entry : sorted_mapWithinGeo.entrySet()) {     // This loop will calculate the Total capacity of all the collector within the geo fance
				Integer value = entry.getValue();
				totalWGcapacity = totalWGcapacity + value;
			}
			if (totalWGcapacity >= requestedQuentity)		//It will check if the collectors within geo fance can carry the requested food
				while (requestedQuentity > 0) {
					boolean isFound = false;
					String lastKey = "";

					for (Map.Entry<String, Integer> entry : sorted_mapWithinGeo.entrySet()) {    //This will check any single node is capable of carrying the food
						String key = entry.getKey();
						Integer value = entry.getValue();
						if (requestedQuentity <= value) {
							isFound = true;
							allocatedResources.add(key);
							// sorted_mapWithinGeo.remove(key);
							requestedQuentity = requestedQuentity - value;
							System.out.println("inside GEO AllocatedResource is :"+ key + " => " + value);
							status = FCSConstants.ALLOCATION_STATUS_ALLOCATED_WG;						
							break;
						}
						lastKey = key;
					}
					if (!isFound) {															//If the single node is not capable then this block will devide it by assigning the most capable collector and removing it from collector list
						System.out.println(sorted_mapWithinGeo.lastEntry());
						allocatedResources.add(lastKey);
						requestedQuentity = requestedQuentity- sorted_mapWithinGeo.lastEntry().getValue();
						System.out.println("inside GEO AllocatedResource last element is :"+ lastKey+ " => "+sorted_mapWithinGeo.lastEntry().getValue());
						status = FCSConstants.ALLOCATION_STATUS_ALLOCATED_WG;						
						resWithinGeo.remove(lastKey);
						ValueComparator bvcWithinGeo1 = new ValueComparator(resWithinGeo);
						TreeMap<String, Integer> sorted_mapWithinGeo1 = new TreeMap(bvcWithinGeo1);
						sorted_mapWithinGeo1.putAll(resWithinGeo);
						sorted_mapWithinGeo = sorted_mapWithinGeo1;
						
					}
				}
			
			/*Resources of Outside Geo Fances */
			if (status.equals(FCSConstants.ALLOCATION_STATUS_NA)) {				//It will do the same stuff as above for collectors outside geo fances
				int totalOGcapacity = 0;
				for (Map.Entry<String, Integer> entry : sorted_mapOutsideGeo.entrySet()) {
					Integer value = entry.getValue();
					totalOGcapacity = totalOGcapacity + value;
				}
				if (totalOGcapacity >= requestedQuentity)
					while (requestedQuentity > 0) {
						boolean isFound = false;
						String lastKey = "";

						for (Map.Entry<String, Integer> entry : sorted_mapOutsideGeo.entrySet()) {
							String key = entry.getKey();
							Integer value = entry.getValue();
							if (requestedQuentity < value) {
								isFound = true;
								allocatedResources.add(key);
								// sorted_mapOutsideGeo.remove(key);
								requestedQuentity = requestedQuentity - value;
								System.out.println("inside GEO AllocatedResource is :"+ key + " => " + value);
								status = FCSConstants.ALLOCATION_STATUS_ALLOCATED_OG;							
								break;
							}
							lastKey = key;
						}
						if (!isFound) {
							System.out.println(sorted_mapOutsideGeo.lastEntry());
							allocatedResources.add(lastKey);
							requestedQuentity = requestedQuentity- sorted_mapOutsideGeo.lastEntry().getValue();
							System.out.println("outside GEO AllocatedResource last element is :"+ lastKey+ " => "+sorted_mapOutsideGeo.lastEntry().getValue());
							status = FCSConstants.ALLOCATION_STATUS_ALLOCATED_OG;							
							resOutsideGeo.remove(lastKey);
							System.out.println(resOutsideGeo);
							ValueComparator bvcOutsideGeo1 = new ValueComparator(resOutsideGeo);
							TreeMap<String, Integer> sorted_OutsideGeo1 = new TreeMap(bvcOutsideGeo1);
							sorted_OutsideGeo1.putAll(resOutsideGeo);
							sorted_mapOutsideGeo = sorted_OutsideGeo1;
						}
					}
			}
			if (!status.equals(FCSConstants.ALLOCATION_STATUS_NA)) {
				return new ResourceAllocationBean(status, allocatedResources); // It will return the found result with collector names(email Id's)
			}			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;					//As no collector found it will return null
	}

}
