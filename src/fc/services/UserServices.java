package fc.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import com.constant.FCSConstants;

import fc.dao.UserDao;
import fc.util.DistanceConverter;
import fc.util.FCSUtil;
import fc.util.ValueComparator;
import fcs.bean.ResourceAllocationBean;
import fcs.bean.User;

public class UserServices {
	UserDao userDao;
	private static Integer GEO_FENCE = 7; 
	
	public UserServices() {
		userDao = new UserDao();
	}
	
	public String loginAdmin(User user){
		if(userDao.authenticateUser(user)){
			user.setPassword(getRandomNumber()+"");
			if(userDao.setToken(user))
				return user.getPassword();
		}			
		return "-1";
	}
	/*This will call dao to check the collector login status
	 * */
	public boolean collectorLogin(User user){
		return userDao.collectorLogin(user);			
	}
	
	/*This will return the assigned requests*/
	public List<User> requestForCollector(User user){
		return userDao.requestForCollector(user);			
	}
	/*This will update the status*/
	public boolean updateRequestStatus(User user){
		return userDao.updateRequestStatus(user);			
	}
	
	/*update location*/
	public boolean updateLocation(User user){
		return userDao.updateLocation(user);			
	}
	
	/*This will generate the random number*/
	private int getRandomNumber(){
		System.out.println("Inside ramdom number generator");
		int randomNum;
		Random rn = new Random();
		int n = 999999999 - 1111111111 + 1;
		int i = rn.nextInt() % n;
		randomNum =  1111111111 + i;
		System.out.println("Random number generated as "+randomNum);
		return randomNum;
	}

	public boolean credentialsCheck(User user) {
		return userDao.credentialsCheck(user);
	}

	public String signUpUser(User user) {
		return userDao.signupUser(user);
	}

	public List<User> getRegistereduser() throws Exception {
		return userDao.getRegisteredUser();
	}
	
	public List<User> getCollectionRequest() throws Exception {
		return userDao.getCollectionRequest();
	}

	public boolean deleteuser(String email) {
		return userDao.deleteUser(email);		
	}
	
	public boolean deleteFCReq(String email) {
		return userDao.deleteFCReq(email);		
	}

	public boolean updateUser(User user) {
		return userDao.updateUser(user);	
	}

	public boolean logout() {
		return userDao.logout();
	}

	public String foodCollectionRequest(User user) throws SQLException {
		List<String> collectorList = allocateCollector(user.getLocation(),user.getRequestedQuantity());
		if(collectorList!=null){
			return userDao.foodCollectionRequest(user,"REQ"+getRandomNumber(),collectorList);	
		}
		return FCSConstants.COLLECTOR_CANNOT_ASSIGNED;			
	}
	private List<String> allocateCollector(String location, String quantity) throws SQLException{
		 Map<String, String> allCollector =  userDao.getAllActiveCollectore();		 
		 Set<String> keys =  allCollector.keySet();
		 Map<String, Integer> withinGeoF = new HashMap<String, Integer>();
		 Map<String, Integer> outsideGeoF = new HashMap<String, Integer>();
		 FCSUtil fcsUtil = new FCSUtil();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = allCollector.get(key);
			if(DistanceConverter.distance(Double.parseDouble(location.split(",")[0]), Double.parseDouble(location.split(",")[1]), Double.parseDouble(value.split("#")[1].split(",")[0]), Double.parseDouble(value.split("#")[1].split(",")[1]), "K")>GEO_FENCE){
				outsideGeoF.put(key, Integer.parseInt(value.split("#")[0]));
			}else{
				withinGeoF.put(key, Integer.parseInt(value.split("#")[0]));
			}			
		}
		outsideGeoF = new HashMap<String, Integer>();
		//System.out.println(DistanceConverter.distance(Integer.parseInt(location.split(",")[0]), Integer.parseInt(location.split(",")[0]), 18.5073985, 73.8076504, "K"));
		System.out.println("Outside gof : "+outsideGeoF.size() +" Indide gof : "+withinGeoF.size());
		ResourceAllocationBean allocationBean = fcsUtil.resourceAllocation(quantity, withinGeoF, outsideGeoF);		
		if(allocationBean!=null && allocationBean.getAllocatedResourcesIds().size()>0){
			System.out.println("Status : "+allocationBean.getStatus() +" Allocated collector :"+allocationBean.getAllocatedResourcesIds());
			userDao.changeCollectorStatus(allocationBean.getAllocatedResourcesIds(),FCSConstants.COLLECTION_REQUEST_ASSIGNED);
			return allocationBean.getAllocatedResourcesIds();
		}else{
			Map<String, Integer> allIdle = userDao.getIdleCollector();
			return allocateIdle(allIdle, Integer.parseInt(quantity)).size()>0?allocateIdle(allIdle, Integer.parseInt(quantity)):null;			
		}
		
	}

	public String foodCollectionRequestStatus(String requestId) throws SQLException {
		return userDao.foodCollectionRequestStatus(requestId);
	}
	
	private List<String> allocateIdle(Map<String, Integer> allIdle, int requestedQuentity){		
		ValueComparator bvcWithinGeo = new ValueComparator(allIdle);
		TreeMap<String, Integer> collector = new TreeMap(bvcWithinGeo);   //This will sort the within geo fence values , for more information read TreeMap of Java Collection
		collector.putAll(allIdle);
		List<String> allocatedResources = new ArrayList<String>();		
		while (requestedQuentity > 0) {
			boolean isFound = false;
			String lastKey = "";

			for (Map.Entry<String, Integer> entry : collector.entrySet()) {    //This will check any single node is capable of carrying the food
				String key = entry.getKey();
				Integer value = entry.getValue();
				if (requestedQuentity <= value) {
					isFound = true;
					allocatedResources.add(key);
					// collector.remove(key);
					requestedQuentity = requestedQuentity - value;
					System.out.println("inside GEO AllocatedResource is :"+ key + " => " + value);
					break;
				}
				lastKey = key;
			}
			if (!isFound) {															//If the single node is not capable then this block will devide it by assigning the most capable collector and removing it from collector list
				System.out.println(collector.lastEntry());
				allocatedResources.add(lastKey);
				requestedQuentity = requestedQuentity- collector.lastEntry().getValue();
				System.out.println("inside GEO AllocatedResource last element is :"+ lastKey+ " => "+collector.lastEntry().getValue());										
				allIdle.remove(lastKey);
				ValueComparator bvcWithinGeo1 = new ValueComparator(allIdle);
				TreeMap<String, Integer> collector1 = new TreeMap(bvcWithinGeo1);
				collector1.putAll(allIdle);
				collector = collector1;
				
			}
		}
		return allocatedResources;		
	}

}
