package com.shopme.admin.brandController;

public class CategoryDTO {
	
	
	private int ID;
	private String name;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CategoryDTO(int iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}
	public CategoryDTO() {
		super();
	}
	
	
}
