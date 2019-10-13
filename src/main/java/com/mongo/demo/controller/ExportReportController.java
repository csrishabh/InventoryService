package com.mongo.demo.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.report.PaymentReportBuilder;
import com.mongo.demo.report.VendorReportBuilder;
import com.mongo.demo.service.ExportReportService;


@RestController
public class ExportReportController {
	
	@Autowired
	private ExportReportService exportReportService;
	
	@Autowired 
	private VendorReportBuilder vendorReportBuilder;
	
	@RequestMapping(value = "/inventory" ,method = RequestMethod.GET)
	public ModelAndView exportPaymentReport(HttpServletResponse response){
		
		Map<String, Object> fileData = exportReportService.exportPaymentReport();
		 response.setContentType( "application/ms-excel" );
	     response.setHeader( "Content-disposition", "attachment; filename=myfile.xls" );
		return new ModelAndView(new PaymentReportBuilder(), fileData);
		
	}
	
	@RequestMapping(value = "/report/vendor" ,method = RequestMethod.GET)
	public AppResponse<String> exporVendorReport(@RequestParam Map<String, Object> filters ){
		AppResponse<String> response = new AppResponse<>();
		vendorReportBuilder.generateVendorReport(filters);
		response.setSuccess(true);
		response.setMsg(Arrays.asList("Report has been send via email"));
		return response;
	}
	

}
