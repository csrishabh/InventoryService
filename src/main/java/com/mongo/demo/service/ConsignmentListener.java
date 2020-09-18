package com.mongo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.mongo.demo.document.Consignment;
import com.mongo.demo.repo.SequenceGenerator;

@Component
public class ConsignmentListener extends AbstractMongoEventListener<Consignment> {

	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Consignment> event) {
	    if (event.getSource().getRefId() < 1) {
	        event.getSource().setRefId(sequenceGenerator.generateSequence(Consignment.SEQUENCE_NAME));
	    }
	}
	
}