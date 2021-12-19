package com.shopme.common.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128 , nullable =  false , unique = true)
	private String email;
	
	@Column(length =  64  , nullable = false)
	private String password;
	
	@Column(name="FirstName" , length = 45 , nullable = false)
	private String firstName;
	
	@Column(name = "LastName"  , length = 45  , nullable =  false)
	private String lastName;
	
	@Column(length = 64)
	private String photo;
	
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(name = "users_role" , joinColumns = @JoinColumn(name ="user_id")  , inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(Integer id, String email, String password, String firstName, String lastName, String photo,
			boolean enabled, Set<Role> roles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photo = photo;
		this.enabled = enabled;
		this.roles = roles;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String email, String password, String firstName, String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role)
	{
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", LastName=" + lastName + ", roles=" + roles + "]";
	}
	
	@Transient
	public String getPhotosImagePath()
	{
		
		if(id == null  || this.photo == null)
		{
			return "/images/default.jpg";
		}
		
		
		return "/Upload_Dir/"+this.id+"/"+this.photo;
		
	}
	
	@Transient
	public String FullName()
	{
		return this.firstName+" "+this.lastName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
