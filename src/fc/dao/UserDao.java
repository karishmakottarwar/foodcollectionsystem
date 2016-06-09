package fc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.constant.FCSConstants;
import com.mysql.jdbc.Statement;

import fc.provider.ConnectionProvider;
import fcs.bean.User;

public class UserDao {
	static Connection connection;
	static{
		try {
			connection = ConnectionProvider.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean authenticateUser(User user){
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "select * from user where username ="+"\""+user.getName()+"\""+" and password=\""+user.getPassword()+"\" and role=\"admin\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/*DB call and auth check of collector*/
	public boolean collectorLogin(User user){
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "select * from user where email ="+"\""+user.getEmail()+"\""+" and password=\""+user.getPassword()+"\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	// User(String name, String email, String mobile, String address,String dateOfBirth, String password)
	/*check food collection requests for collection*/
	public List<User> requestForCollector(User user){
		connection = ConnectionProvider.getConnection();
		System.out.println("user is"+user);
		System.out.println(user.getStatus()==null);
		System.out.println(((user.getStatus()==null)?"":" and req_number ="+user.getStatus().trim().toString()));
		//System.out.println("status : "+user.getStatus().trim().toString().equalsIgnoreCase("null"));
		
			String sqlQuery = "select * from collection_request where req_number in (select RequestNo from request_mapping where collectorIds =\""+user.getEmail()+"\") and status not in('"+FCSConstants.REQUEST_COMPLETED+"','"+FCSConstants.REQUEST_CANCELLED+"')"+((user.getStatus()==null || user.getStatus().trim().toString().equalsIgnoreCase("null") || user.getStatus().trim().toString() == "")?"":" and req_number ='"+user.getStatus().trim().toString()+"'");
			System.out.println("the final query :"+sqlQuery);
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
				ResultSet rs = preparedStatement.executeQuery();
				List<User> users  = new ArrayList<User>();
				users.add(new User());
				while(rs.next()){
					users.add(new User(rs.getString("req_name"), rs.getString("email"), rs.getString("req_contact"), rs.getString("req_address"), "","",rs.getString("req_number"),rs.getString("req_quantity"),rs.getString("req_location") ));
				}
				System.out.println("requests for collector length"+users.size());
				return users;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		System.out.println("not in any if");
		
		return null;
	}
	
	public boolean setToken(User user){
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "update user set token=\""+user.getPassword()+"\" where username=\""+user.getName()+"\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*Update status*/
	public boolean updateRequestStatus(User user){
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "update collection_request set status=\""+user.getStatus().trim()+"\" where req_number='"+user.getRequestedQuantity()+"'";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			connection = ConnectionProvider.getConnection();
			String updateCollectorStatus = "select * from collection_request where status in ('collectionRequestAssigned','"+FCSConstants.REQUEST_COLLECTED+"') and req_number in (select RequestNo from request_mapping where collectorIds='"+user.getEmail()+"')";
			PreparedStatement pstmt  = connection.prepareStatement(updateCollectorStatus);
			ResultSet rs=  pstmt.executeQuery();
			if(rs.next()){
				
			}else{
				connection = ConnectionProvider.getConnection();
				String updateCollector = "update collector_availability set status='Idle' where user_email='"+user.getEmail()+"'";
				connection.prepareStatement(updateCollector).execute();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*update location*/
	public boolean updateLocation(User user){
		connection = ConnectionProvider.getConnection();
		System.out.println("user location is"+user.getLocation());
		if(user.getLocation()!=null && user.getLocation().trim()!="" && !user.getLocation().trim().equalsIgnoreCase("null")){
			String sqlQuery = "update collector_availability set currentLocation=\""+user.getLocation().trim()+"\" where user_email=\""+user.getEmail()+"\"";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}	
		return false;
	}
	
	public String getToken(User user){
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "select * from user where username=\""+user.getName()+"\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				return rs.getString("token");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	
	}

	public boolean credentialsCheck(User user) {
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "select * from user where username ="+"\""+user.getName()+"\""+" and token=\""+user.getPassword()+"\" and role=\"admin\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String signupUser(User user) {
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "insert into user(username,email,mobile,password) value(\""+user.getName()+"\",\""+user.getEmail()+"\",\""+user.getMobile()+"\",\""+user.getPassword()+"\")";
		String sqlQueryCollector = "insert into collector_availability(user_email,status,qty) value(\""+user.getEmail()+"\",\"Idle\",\""+user.getRequestedQuantity()+"\")";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			preparedStatement.execute();
			connection = ConnectionProvider.getConnection();
			preparedStatement = connection.prepareStatement(sqlQueryCollector);
			preparedStatement.execute();
			return "User registered successfully";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Duplicate entry";
		}
	}

	public List<User> getRegisteredUser() throws Exception{
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "select * from user u, collector_availability ca where u.email = ca.user_email and u.email not in (select  email from user where role='admin' ) ";
		List<User> userList = new ArrayList<User>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			ResultSet rs = preparedStatement.executeQuery();			
			while(rs.next()){
				User user = new User();
				user.setName(rs.getString("username"));
				user.setMobile(rs.getString("mobile"));
				user.setEmail(rs.getString("email"));
				user.setStatus(rs.getString("status"));
				userList.add(user);
			}
		} catch(SQLException exception){
			exception.printStackTrace();
			throw exception;
		}
		return userList;
	}
	
	public List<User> getCollectionRequest() throws Exception{
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "select * from collection_request"; // where status not in('"+FCSConstants.REQUEST_COMPLETED+"','"+FCSConstants.REQUEST_CANCELLED+"')";
		List<User> userList = new ArrayList<User>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();	
			System.out.println(sqlQuery +"and execute query result is :"+rs);					
			while(rs.next()){
				User user = new User();
				user.setDateOfBirth(rs.getString("req_number"));
				user.setName(rs.getString("req_name"));
				user.setMobile(rs.getString("req_contact"));
				user.setEmail(rs.getString("email"));
				user.setStatus(rs.getString("status"));
				user.setLocation(rs.getString("req_address"));
				user.setRequestedQuantity(rs.getString("req_quantity"));
				userList.add(user);
			}
		} catch(SQLException exception){
			exception.printStackTrace();
			throw exception;
		}
		return userList;
	}
	
	public boolean deleteUser(String email) {
		connection = ConnectionProvider.getConnection();
		try {
			String sqlQuery = "delete from user where email = \""+email+"\"";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	public boolean deleteFCReq(String reqNo) {
		connection = ConnectionProvider.getConnection();
		try {
			String sqlQuery = "delete from request_mapping where RequestNo = \""+reqNo.trim()+"\"";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.executeUpdate();
			connection = ConnectionProvider.getConnection();
			String sqlQuery2 = "update collection_request  set status='"+FCSConstants.REQUEST_CANCELLED+"' where req_number= \""+reqNo.trim()+"\"";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
			preparedStatement2.executeUpdate();
			connection = ConnectionProvider.getConnection();
			String sqlQuery3 = "update collector_availability set status =\"Idle\" where user_email not in (select collectorids from request_mapping)";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sqlQuery3);
			preparedStatement3.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}		
		return true;
	}

	public boolean updateUser(User user) {		
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "update user set mobile=\""+user.getMobile()+"\",username=\""+user.getName()+"\" where email=\""+user.getEmail()+"\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean logout() {
		connection = ConnectionProvider.getConnection();
		String sqlQuery = "update user set token=\"-1\" where role=\"admin\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public String foodCollectionRequest(User user, String requestNumber, List<String> collectorDetails) throws SQLException {
		connection = ConnectionProvider.getConnection();
		System.out.println("into UserDao.foodCollectionRequest with user :"+user);
		try {
			for (int i = 0; i < collectorDetails.size(); i++) {
				String sqlReqMappingQuery = "insert into request_mapping(RequestNo,collectorIds) values('"+requestNumber+"','"+collectorDetails.get(i)+"')";
				System.out.println("sqlReqMappingQuery final:"+sqlReqMappingQuery);
				PreparedStatement pstmtRMQ = connection.prepareStatement(sqlReqMappingQuery);
				pstmtRMQ.executeUpdate();
				connection = ConnectionProvider.getConnection();
				String updateStatus = "update collector_availability set status=\""+FCSConstants.COLLECTION_REQUEST_ASSIGNED+"\" where user_email=\""+collectorDetails.get(i)+"\"";
				connection.prepareStatement(updateStatus).executeUpdate();
			}
			
			connection = ConnectionProvider.getConnection();
			String sqlQuery = "insert into collection_request(req_name,req_location,req_address,req_contact,req_quantity,email,req_number,status) value(\""+user.getName()+"\",\""+user.getLocation()+"\",\""+user.getAddress()+"\",\""+user.getMobile()+"\",\""+user.getRequestedQuantity()+"\",\""+user.getEmail()+"\",\""+requestNumber+"\",\""+FCSConstants.COLLECTION_REQUEST_ASSIGNED+"\")";
			System.out.println("final insert query"+sqlQuery);
			PreparedStatement pstmt= connection.prepareStatement(sqlQuery);
			pstmt.executeUpdate();
			return requestNumber;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	public Map<String, String> getAllActiveCollectore() {
		connection = ConnectionProvider.getConnection();
		Map<String, String> collector = new HashMap<String, String>();
		String sqlQuery = "select * from collector_availability where status not in (\"Idle\",\""+FCSConstants.REQUEST_CANCELLED+"\")";		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery +"and execute query result is :"+preparedStatement.execute());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				System.out.println("alive collector is : select sum(req_quantity) as sum from collection_request where req_number in (select RequestNo from request_mapping where collectorIds=\""+rs.getString("user_email")+"\")");
				ResultSet rsSum = connection.prepareStatement("select sum(req_quantity) as sum from collection_request where status in('collectionRequestAssigned','"+FCSConstants.REQUEST_COLLECTED+"') and req_number in (select RequestNo from request_mapping where collectorIds=\""+rs.getString("user_email")+"\")").executeQuery();
				if(rsSum.next())
				collector.put(rs.getString("user_email"), (Integer.parseInt(rs.getString("qty"))-rsSum.getInt("sum"))+"#"+rs.getString("currentLocation"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("collector is "+collector);
		return collector;
	}

	public boolean changeCollectorStatus(List<String> allocatedResourcesIds,String status) throws SQLException {
		connection = ConnectionProvider.getConnection();
		String idsToUpdate="";
		for (int i = 0; i < allocatedResourcesIds.size(); i++) {
			if(i==0){
				idsToUpdate= "'"+allocatedResourcesIds.get(i)+"'";
			}else{
				idsToUpdate = idsToUpdate + ",'"+allocatedResourcesIds.get(i)+"'";
			}
		}
		String query = "update collector_availability set status='"+status+"' where user_email in ("+idsToUpdate+")";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			if(pstmt.executeUpdate()>0){
				return true;			
			}else{
				return false;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
			throw e;
		}
		
	}

	public String foodCollectionRequestStatus(String requestId) throws SQLException {
		connection = ConnectionProvider.getConnection();
		String query = "select status from collection_request where req_number='"+requestId+"'";
		ResultSet rs;
		try {
			PreparedStatement preparedStatement  = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();			
		} catch (SQLException e) {			
			e.printStackTrace();
			throw e;
		}
		return rs.next() ? rs.getString("status") : "The request id is not present.Enter valid one";
	}

	public Map<String, Integer> getIdleCollector() {
		connection = ConnectionProvider.getConnection();
		Map<String, Integer> result = new HashMap<String, Integer>();
		try {
			ResultSet rs = connection.prepareStatement("select * from collector_availability where status=\"Idle\"").executeQuery();			
			while(rs.next()){
				result.put(rs.getString("user_email"),Integer.parseInt(rs.getString("qty")));
			}
			System.out.println("The final Idle resultset is"+result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	}
}
