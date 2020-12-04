package com.restapi.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity

public class Product {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

@Column(name="PROD_ID",nullable=false)
private long product_id;

@Column(name="PROD_NAME")
private String prod_name;


@Column(name="PROD_BRAND")
private String prod_brand;


@Column(name="PROD_MADEIN")
private String prod_madein;

@Column(name="PRICE")
private float price;


public float getPrice() {
	return price;
}

public void setPrice(float price) {
	this.price = price;
}

public Product() {
	super();
}

public long getProduct_id() {
	return product_id;
}
public void setProduct_id(long product_id) {
	this.product_id = product_id;
}
public String getProd_name() {
	return prod_name;
}
public void setProd_name(String prod_name) {
	this.prod_name = prod_name;
}
public String getProd_brand() {
	return prod_brand;
}
public void setProd_brand(String prod_brand) {
	this.prod_brand = prod_brand;
}
public String getProd_madein() {
	return prod_madein;
}
public void setProd_madein(String prod_madein) {
	this.prod_madein = prod_madein;
}
@Override
public String toString() {
	return "Product [product_id=" + product_id + ", prod_name=" + prod_name + ", prod_brand=" + prod_brand
			+ ", prod_madein=" + prod_madein + ", price=" + price + "]";
}


}
