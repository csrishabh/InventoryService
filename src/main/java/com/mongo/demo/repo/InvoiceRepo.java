package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mongo.demo.document.Invoice;

public interface InvoiceRepo extends MongoRepository<Invoice, String>, InvoiceRepoCustom {

	@Query("{'refId' : ?0, 'isCurrent' : true , 'isCancelled' : false}")
	Invoice getCurrentInvoiceByrefId(long refId);
}
