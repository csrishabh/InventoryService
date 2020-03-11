package com.mongo.demo.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter @Setter
public class Manifest {
	
	@Id
	String id;
	
	@Transient
	public static final String SEQUENCE_NAME = "manifest_sequence";
	
	private long refId;
	
	private String company;
	
	private int unitComMappingVer;
	
	private Date bookingDate;
	
	private String des;
	
	private Unit paidBy;
	
	private List<String> consignments;
	
}
