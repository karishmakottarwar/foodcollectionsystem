package fcs.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fc.services.UserServices;
import fcs.bean.User;

@Path("/user")
public class UserResource {
	
	UserServices userServices;
	public UserResource() {
		userServices = new UserServices();
	}
		
	@POST
	@Path("signUp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String registerUser(User user){
		return "success";
	}
	@GET
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public User loginAdmin(@QueryParam("name") String name, @QueryParam("password") String password){		
		System.out.println(name+"###"+password);
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setPassword(userServices.loginAdmin(user));
		return user;
	}
	@GET
	@Path("signUp")
	public String signUpUser(@QueryParam("name") String name,@QueryParam("email") String email,@QueryParam("mobile") String mobile,@QueryParam("password") String password,@QueryParam("quantity")String quantity){
		User user = new User(name, email, mobile, "", "", password);
		user.setRequestedQuantity(quantity);
		//userServices.signUpUser(user);
		System.out.println("Into Signup method");
		System.out.println(user.toString());
		return userServices.signUpUser(user);
	}
	
	@GET
	@Path("checkLogin")
	public String credentialsCheck(@QueryParam("name") String name, @QueryParam("password") String password){
		User user = new User(name, "", "", "", "", password);
		return userServices.credentialsCheck(user)==true ? "true" : "false";
	}
	
	/*the method to get all registered user*/
	@GET
	@Path("getRegistereduser")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getRegistereduser(){
		try{
			return userServices.getRegistereduser();
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}		
	}
	/*the method to get all registered user*/
	@GET
	@Path("getCollectionRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getCollectionRequest(){
		try{
			return userServices.getCollectionRequest();
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}		
	}
	/*the methoddelete the user */
	@GET
	@Path("deleteUser")
	public String deleteUser(@QueryParam("email")String email){		
		try{
			if(userServices.deleteuser(email))
				return "success";
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
		return email;		
	}
	
	/*the method delete the FC req*/
	@GET
	@Path("deleteFCReq")
	public String deleteFCReq(@QueryParam("request_no")String email){		
		try{
			if(userServices.deleteFCReq(email))
				return "success";
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
		return email;		
	}
	
	/*update the user data */
	@GET
	@Path("updateUser")
	public String updateUser(@QueryParam("email")String email, @QueryParam("name")String name,@QueryParam("mobile")String mobile){		
		try{
			if(userServices.updateUser(new User(name, email, mobile, "", "", "")))
				return "success";
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}	
		return null;
	}
	
	/*logout the user data */
	@GET
	@Path("logout")
	public String logout(){		
		try{
			if(userServices.logout())
				return "success";
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}	
		return null;
	}
	@GET
	@Path("address/{address}")
	public List<String> getAddress(@PathParam("address")String address){
		System.out.println("into address.............."+"https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyD_7jZK5UXt287KSmlC6Fga1_RTavxUX1M");
		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyD_7jZK5UXt287KSmlC6Fga1_RTavxUX1M");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode()+conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@GET
	@Path("theCollectionRequest")
	public String foodCollectionRequest(@QueryParam("name")String name,@QueryParam("requestQuantity")String requestedQuentity,@QueryParam("mobileNumber")String mobileNumber,@QueryParam("emailId")String email, @QueryParam("address")String address,@QueryParam("location")String location){
		
		User user = new User();
		user.setName(name);
		user.setRequestedQuantity(requestedQuentity);
		user.setMobile(mobileNumber);
		user.setEmail(email);
		user.setAddress(address);
		user.setLocation(location);
		System.out.println("in UserResource.foodCollectionRequest: user "+user);
		try{
			return userServices.foodCollectionRequest(user);
		}catch(Exception e){
			e.printStackTrace();
			return "-1";
		}
	}
	
	@GET
	@Path("fetchRequestStatus")
	public String foodCollectionRequestStatus(@QueryParam("requestId")String requestId){
		
		System.out.println("in UserResource.foodCollectionRequestStatus: requestId "+requestId);
		try{
			return userServices.foodCollectionRequestStatus(requestId);
		}catch(Exception e){
			e.printStackTrace();
			return "Error Occured, try after some time!!";
		}
	}
	
	@GET
	@Path("collectorLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public User loginCollector(@QueryParam("username")String uName,@QueryParam("password")String uPwd){
		User  user = new User("", uName, "", "", "", uPwd);
		System.out.println("User fetched is :"+user);
		if(!userServices.collectorLogin(user)){
			user.setEmail("error");
		}
		return user;
	}
	
	@GET
	@Path("requestForCollector")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> requestForCollector(@QueryParam("username")String uName,@QueryParam("requestNumber")String requestNumber){
		User  user = new User("", uName, "", "", "", "", requestNumber, "", "");
		System.out.println("User fetched is :"+user);
		return userServices.requestForCollector(user);
	}
	@GET
	@Path("updateRequestStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public User updateRequestStatus(@QueryParam("username")String uName,@QueryParam("status")String status,@QueryParam("requestNumber")String requestNumber ){
		User  user = new User("", uName, "", "", "", "", status, requestNumber, "");
		System.out.println("User fetched for status update is :"+user);
		return userServices.updateRequestStatus(user)?new User("", uName, "", "", "", ""):null;
	}
	@GET
	@Path("updateLocation")
	@Produces(MediaType.APPLICATION_JSON)
	public User updateLocation(@QueryParam("username")String uName,@QueryParam("location")String location){
		User  user = new User("", uName, "", "", "", "", "", "", location);
		System.out.println("User fetched for location is :"+user);
		return userServices.updateLocation(user)?new User("", uName, "", "", "", ""):null;
	}
}
