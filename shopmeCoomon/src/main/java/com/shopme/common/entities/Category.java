package com.shopme.common.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name =  "Category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128 , nullable =  false , unique = true)
	private String name;
	
	@Column(length = 64 , nullable =  false , unique = true)
	private String alise;
	
	@Column(length = 128 , nullable =  false)
	private String image;
	
	
	private boolean enable;
	
	@OneToOne
	@JoinColumn(name= "parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<Category> children = new HashSet<>();

	
	public Category(String name) {
		
		this.name = name;
		this.alise = name;
		this.image = "deafult.png";
	}
	
	public static Category copyIdAndName(Category category)
	{
		Category copyCategory =  new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		
		return copyCategory;
		
	}
	
	public static Category copyIdAndName(Integer id , String name)
	{
		Category copyCategory =  new Category();
		copyCategory.setId(id);
		copyCategory.setName(name);
		return copyCategory;
		
	}

	public Category() {
		super();
	}
	public static Category copyFullName(Category category)
	{
			Category copycategory  = new Category();
			copycategory.setId(category.getId());
			copycategory.setName(category.getName());
			copycategory.setImage(category.getImage());
			copycategory.setAlise(category.getAlise());
			copycategory.setEnable(category.isEnable());
			copycategory.setHasChildren(category.getChildren().size() > 0);
			
			return copycategory;
			
	}
	
	public Category(Integer id, String name, String alise) {
		super();
		this.id = id;
		this.name = name;
		this.alise = alise;
	}

	public static Category copyFull(Category category , String name)
	{
		Category copycategory =  Category.copyFullName(category);
		copycategory.setName(name);
		return copycategory;
		
		
	}

	public Category(Integer id) {
		this.id = id;
	}

	public Category(String name , Category parent)
	{
		this(name);
		this.parent =  parent;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlise() {
		return alise;
	}

	public void setAlise(String alise) {
		this.alise = alise;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	
	@Transient
	public String getImagepath()
	{
		if(this.id==null) {
			return "/images/image-thumbnail.png";
		}
		return "/category_image/"+this.id+"/"+this.image;
	}
	
	@Transient
	private boolean hasChildren;
	
	public void setHasChildren(boolean hasChildren)
	{
		this.hasChildren =  hasChildren;
	}
	public boolean isHasChildren()
	{
		return this.hasChildren;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
	
	
	
	
}
