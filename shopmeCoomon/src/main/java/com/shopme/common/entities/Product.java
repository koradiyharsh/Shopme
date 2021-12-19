package com.shopme.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name  = "Product")
public class Product {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique =  true ,length =  256 , nullable = false)
	private String name;
	
	@Column(unique =  true ,length =  256 , nullable = false)
	private String alise;
	
	@Column(length = 512 , nullable =  false , name = "short_description")
	private String shortDescription;
	
	@Column(length = 4096 , nullable = false , name = "full_description")
	private String FullDescription;
	
	@Column(name= "created_time")
	private Date createdTime;
	
	@Column(name =  "updated_time")
	private Date updatedTime;
	
	
	private boolean enabled;
	
	@Column(name = "in_stock")
	private boolean in_stock;
	
	
	private float cost;
	
	private float price;
	
	@Column(name = "discount_percent")
	private float discountpercent;
	
	private float length;
	private float weight;
	private float width;
	private float height;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return FullDescription;
	}

	public void setFullDescription(String fullDescription) {
		FullDescription = fullDescription;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	

	public boolean isIn_stock() {
		return in_stock;
	}

	public void setIn_stock(boolean in_stock) {
		this.in_stock = in_stock;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiscountpercent() {
		return discountpercent;
	}

	public void setDiscountpercent(float discountpercent) {
		this.discountpercent = discountpercent;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", alise=" + alise + ", shortDescription=" + shortDescription
				+ ", FullDescription=" + FullDescription + ", createdTime=" + createdTime + ", updatedTime="
				+ updatedTime + ", enabled=" + enabled + ", instock=" + in_stock + ", cost=" + cost + ", price=" + price
				+ ", discountpercent=" + discountpercent + ", length=" + length + ", weight=" + weight + ", width="
				+ width + ", height=" + height + ", category=" + category + ", brand=" + brand + "]";
	}
	
	
	
	
	
}
