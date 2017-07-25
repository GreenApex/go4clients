package com.ga.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "companyname")
	private String companyName;

	@Column(name = "companyaddress")
	private String companyAddress;

	@Column(name = "companystate")
	private String companyState;

	@Column(name = "userpassword")
	private String userPassword;

	@Column(name = "companyzip")
	private String companyZip;

	@NotNull(message = "Name is required")
	@Size(min = 3, max = 50, message = "name must be longer than 3 and less than 40 characters")
	private String name;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@NotNull(message = "Email is required")
	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Must be valid email")
	private String email;

	@Column(name = "isadmin", nullable = false)
	private Boolean isAdmin = false;

	public Employee() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyState() {
		return companyState;
	}

	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}

	public String getCompanyZip() {
		return companyZip;
	}

	public void setCompanyZip(String companyZip) {
		this.companyZip = companyZip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "Person{" + "id=" + id + ", companyName='" + companyName + '\''
				+ ", companyAddress='" + companyAddress + '\''
				+ ", companyState='" + companyState + '\'' + ", userPassword='"
				+ userPassword + '\'' + ", companyZip='" + companyZip + '\''
				+ ", name='" + name + '\'' + ", lastName='" + lastName + '\''
				+ ", phoneNumber='" + phoneNumber + '\'' + ", email='" + email
				+ '\'' + '}';
	}
}
