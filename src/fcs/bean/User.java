package fcs.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	String name,email,mobile,Address,dateOfBirth,password,status,requestedQuantity,location;
	
	public User() {
		
	}
	
	public User(String name, String email, String mobile, String address,
			String dateOfBirth, String password) {
		super();
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		Address = address;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
	}
	
	

	public User(String name, String email, String mobile, String address,
			String dateOfBirth, String password, String status,
			String requestedQuantity, String location) {
		super();
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		Address = address;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.status = status;
		this.requestedQuantity = requestedQuantity;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	

	public String getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(String requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", mobile=" + mobile
				+ ", Address=" + Address + ", dateOfBirth=" + dateOfBirth
				+ ", password=" + password + ", status=" + status
				+ ", requestedQuantity=" + requestedQuantity + ", location="
				+ location + "]";
	}	
	
}
