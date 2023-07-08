package com.fullstack.backendjwt.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.backendjwt.model.AuthenticationReq;
import com.fullstack.backendjwt.model.Product;
import com.fullstack.backendjwt.repository.ProductRepository;
import com.fullstack.backendjwt.util.JwtUtil;

import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("api/v1/")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
    private JwtUtil jwtUtil;
	
    @Autowired
    private AuthenticationManager authenticationManager;

	@PostConstruct
	public void initProducts() {
		List<Product> products = Stream.of(
				new Product(1001,"Laptop",100000,2),
				new Product(1002,"Chair",8000,4)
				).collect(Collectors.toList());
		productRepository.saveAll(products);
	}

	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@PostMapping("/create-product")
	public Product createProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	
	@PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthenticationReq request) throws Exception {
        try {
        	System.out.println("Request aaaa gai haaaa");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(request.getUserName());
    }
}
