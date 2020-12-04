package com.packageproduct.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.product.entity.Product;
import com.restapi.product.repository.ProductRepository;

@Service
public class ProductService {
	
	
	@Autowired
	ProductRepository productrepo;
	
	
	public List<Product> listall(){
		return productrepo.findAll();
	}
	
	
	public void save(Product product) {
		
		productrepo.save(product);
	}
	
	public Product get(Long prod_id) {
		return productrepo.findById(prod_id).get();
	}
	
	
	public void delete (Long prod_id) {
		productrepo.deleteById(prod_id);
	}
}
