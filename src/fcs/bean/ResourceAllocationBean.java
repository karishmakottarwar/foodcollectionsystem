package fcs.bean;

import java.util.List;

/**
 * @author PRASHANT
 *
 */
public class ResourceAllocationBean {
	String status;
	List<String> allocatedResourcesIds;	
	
	public ResourceAllocationBean(String status, List<String> allocatedResourcesIds) {
		super();
		this.status = status;
		this.allocatedResourcesIds = allocatedResourcesIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getAllocatedResourcesIds() {
		return allocatedResourcesIds;
	}
	public void setAllocatedResourcesIds(List<String> allocatedResourcesIds) {
		this.allocatedResourcesIds = allocatedResourcesIds;
	}
	
	@Override
	public String toString() {
		return "[ allocatedResourcesIds=" + allocatedResourcesIds + "]";
	}
	
}
