package entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Student extends Person {
	private String parentsFirstPhoneNo;
	private String parentsSecondPhoneNo;
	private MemorizationGroup group;

	public String getParentsFirstPhoneNo() {
		return parentsFirstPhoneNo;
	}

	public void setParentsFirstPhoneNo(String parentsFirstPhoneNo) {
		this.parentsFirstPhoneNo = parentsFirstPhoneNo;
	}

	public String getParentsSecondPhoneNo() {
		return parentsSecondPhoneNo;
	}

	public void setParentsSecondPhoneNo(String parentsSecondPhoneNo) {
		this.parentsSecondPhoneNo = parentsSecondPhoneNo;
	}

	@ManyToOne
	@JoinColumn(name = "group_id")
	public MemorizationGroup getGroup() {
		return group;
	}

	public void setGroup(MemorizationGroup group) {
		this.group = group;
	}

	@Transient
	public String getGroupName() {
		return group != null ? group.getName() : "";
	}

}
