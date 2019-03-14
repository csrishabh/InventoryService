package com.mongo.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.Product;
import com.mongo.demo.document.Transction;
import com.mongo.demo.document.TransctionType;
import com.mongo.demo.document.User;
import com.mongo.demo.repo.ProductRepo;
import com.mongo.demo.repo.TransctionRepo;
import com.mongo.demo.service.CustomUserDetailsService;
import com.mongo.demo.service.EmailService;

@RestController
public class TransctionController {

	@Autowired
	private TransctionRepo tRepo;

	@Autowired
	private ProductRepo pRepo;
	
	@Autowired 
	private CustomUserDetailsService userService;
	
	@Autowired
	private EmailService emailService;

	@PostMapping("/addTransction")
	public ResponseEntity<Transction> addTrasction(@RequestBody Transction transction) {
		try {
			Product product = new Product();
			product.setId(transction.getProductId());
				transction.setAddBy(SecurityContextHolder.getContext().getAuthentication().getName());
				if (transction.getType() == TransctionType.ADD) {
					transction.setAdtable(true);
					product.setLstAddDate(new Date());
					product.setLstAddBy(SecurityContextHolder.getContext().getAuthentication().getName());
					product = pRepo.updateProduct(product, TransctionType.ADD, transction.getQuantity());
					if(product == null) {
						return new ResponseEntity<Transction>(HttpStatus.NOT_FOUND);
					}
				} else if (transction.getType() == TransctionType.DISPATCH) {
						 product = pRepo.updateProduct(product, TransctionType.DISPATCH, transction.getQuantity());
						 if(product == null) {
								return new ResponseEntity<Transction>(transction,HttpStatus.NOT_ACCEPTABLE);
				}
					if(product.getQtyAbl() < product.getAlert()) {
						emailService.sendAlertMail(product);
					}
				}
				Transction t = tRepo.save(transction);
				return new ResponseEntity<Transction>(t, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Transction>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/delTransction")
	public ResponseEntity<Transction> deleteTrasction(@RequestBody Transction transction) {
		try {
			if(transction.getId()!=null) {
				Transction t = tRepo.deleteTransction(transction);
				if(t == null) {
					return new ResponseEntity<Transction>(HttpStatus.NOT_ACCEPTABLE);
				}
				return new ResponseEntity<Transction>(HttpStatus.OK);
			}
			return new ResponseEntity<Transction>(HttpStatus.NOT_FOUND);
			
		}
		catch (Exception e) {
			return new ResponseEntity<Transction>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/audit/transction")
	public ResponseEntity<List<Object[]>> getAuditableTransctions(@RequestParam Map<String, String> map) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			List<Transction> transctions = tRepo.getAuditPandingTransction(formatter.parse(map.get("startDate")), formatter.parse(map.get("endDate")));
			
			List<Object[]> response = new ArrayList<>();
			
			
			transctions.stream().forEach(t ->{
				Product p = pRepo.findById(t.getProductId()).get();
				User user = userService.findUserByEmail(t.getAddBy());
				Object[] arr = new Object[7];
				arr[0] = formatter.format(t.getDate());
				arr[1] = p.getName();
				arr[2] = new Double(t.getQuantity());
				arr[3] = new Double(t.getAmount());
				arr[4]= user.getFullname();
				arr[5]= String.valueOf(p.getLastPrice());
				arr[6]= t.getId();
				response.add(arr);
			});
			
			return new ResponseEntity<List<Object[]>>(response, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<List<Object[]>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/addTransctions")
	public ResponseEntity<List<Transction>> saveTransctions( @RequestBody List<Transction> transctions) {
		List<Transction> unSuccessFulTrns = new ArrayList<>();
		try {
			
			transctions.stream().forEach(t->{
			ResponseEntity<Transction> res =  addTrasction(t);
			if(res.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
				unSuccessFulTrns.add(res.getBody());
			}
			});
			if(unSuccessFulTrns.size()>0) {
				return new ResponseEntity<>(unSuccessFulTrns,HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/audit")
	public ResponseEntity<Transction> auditTransction( @RequestBody Transction transction) {
		try {
			Transction trns = new Transction();
			Product product = new Product();
			trns.setId(transction.getId());
			double price = transction.getAmount() / transction.getQuantity();
			price = Math.round(price * 100.0) / 100.0;
			product.setLastPrice(price);
			product.setLstAdtBy(SecurityContextHolder.getContext().getAuthentication().getName());
		
			
			trns.setAmount(transction.getAmount());
			trns.setQuantity(transction.getQuantity());
			trns.setAdtBy(SecurityContextHolder.getContext().getAuthentication().getName());
			trns.setType(TransctionType.AUDIT);
			trns = tRepo.updateTransction(trns);
			if(trns==null) {
				return new ResponseEntity<Transction>(HttpStatus.NOT_ACCEPTABLE);
			}
			product.setId(trns.getProductId());
			product = pRepo.updateProduct(product, TransctionType.AUDIT, transction.getQuantity());
			return new ResponseEntity<Transction>(trns, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Transction>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


}
