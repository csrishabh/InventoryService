package com.mongo.demo.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.Case;
import com.mongo.demo.document.CaseSearchResult;
import com.mongo.demo.document.CaseStatus;
import com.mongo.demo.document.User;
import com.mongo.demo.repo.CaseRepo;
import com.mongo.utility.Config;
import com.mongo.utility.StringConstant;

@Service
public class CaseService {
	
	@Autowired
	private CaseRepo caseRepo;
	
	@Autowired
	private CustomUserDetailsService userService;
	
	
	public AppResponse<Case> saveNewCase(Case report) {
		AppResponse<Case> response = new AppResponse<>();
		try {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		report.setStatus(CaseStatus.BOOKED);
		report.setCreatedBy(userId);
		if(null == report.getBookingDate()) {
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.BOOKING_DATE_NOT_FOUND));
			return response;	
		}
		if(null == report.getAppointmentDate()) {
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.APPOINTMENT_DATE_NOT_FOUND));
			return response;
		}
		if(null == report.getDeliveredDate()){
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.DELIVERY_DATE_NOT_FOUND));
			return response;
		}
		if(null == report.getOpdNo()){
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.OPD_NO_NOT_FOUND));
			return response;
		}
		if(null == report.getCrown() || null == report.getCrown().getDetails() 
				|| report.getCrown().getDetails().size() == 0 || StringUtils.isEmpty(report.getCrown().getShade())) {
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.CROWN_NOT_FOUND));
			return response;
		}
		
		Case duplicateCase = caseRepo.getCaseByOpdAndVersionNo(report.getOpdNo(), 1);
		
		if(null != duplicateCase) {
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.FOUND_DUPLICATE_CASE));
			return response;
		}
		report.setOpdNo(report.getOpdNo().toUpperCase());
		report.setBookingDate(Config.fomatDate(report.getBookingDate()));
		report.setDeliveredDate(Config.fomatDate(report.getDeliveredDate()));
		report.setUpdateBy(userId);
		report.setUpdateDate(Config.fomatDate(new Date()));
		report.setVersion(1);
		report = caseRepo.save(report);	
		response.setData(report);
		response.setSuccess(true);
		response.setMsg(Arrays.asList(StringConstant.CASE_CREATED_SUCCESS));
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
			return response;
		}
		return response;
	}
	
	public List<CaseSearchResult> getCaseHistory(Map<String, Object> filters){
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUserByEmail(userId);
		List<CaseSearchResult> cases ;
		if(user.getRoles().contains("ADMIN")) {
			cases = caseRepo.findAllLatestCase(filters);
		}
		else {
		cases = caseRepo.findAllLatestCaseByUser(user.getUsername(),filters);
		cases.stream().forEach(c->{
			if(!DateUtils.isSameDay(c.getCase().getBookingDate(), new Date())) {
				c.setEditable(false);
			}
		});
		}
		final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy hh.mm aa");
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		cases.stream().forEach(c ->{
			c.setAppointmentDate(dateTimeFormat.format(c.getCase().getAppointmentDate()));
			c.setBookingDate(dateFormat.format(c.getCase().getBookingDate()));
			c.setDeliverdDate(dateFormat.format(c.getCase().getDeliveredDate()));
			c.setPatientName(c.getCase().getPatient().getName());
			c.setVendorName(c.getCase().getVender().getName());
			c.setDoctorName(c.getCase().getDoctor().getName());
			c.setStatus(c.getCase().getStatus().toString());
			c.setCreatedBy(c.getCase().getCreatedBy());
			c.setActions(c.getCase().getnextActions());
			c.setCrownDetails(c.getCase().getCrown().toString());
			c.setCase(null);
		});
		return cases;
		
	}
	
	public AppResponse<Document> getLateCaseCount(){
		
		AppResponse<Document> response = new AppResponse<>();
		try {
			List<Document> doc = caseRepo.findLateCaseCount();
			if(null != doc && doc.size() > 0 ) {
				response.setSuccess(true);
				response.setData(doc.get(0));
			}
			else {
				response.setSuccess(false);
			}	
		}
		catch (Exception e) {
			response.setSuccess(false);
		}
		
		return response;
	}
	
	public AppResponse<List<CaseSearchResult>> getAllLateCase(){
		AppResponse<List<CaseSearchResult>> response = new AppResponse<>();
		try {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.findUserByEmail(userId);
			List<CaseSearchResult> cases = caseRepo.findAllLateCase();
			if(!user.getRoles().contains("ADMIN")) {
				cases.stream().forEach(c->{
					if(!DateUtils.isSameDay(c.getCase().getBookingDate(), new Date()) || c.getCase().getCreatedBy().equals(userId)){
						c.setEditable(false);
					}
					
				});
				
			}
			final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy hh.mm aa");
			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			cases.stream().forEach(c ->{
				c.setAppointmentDate(dateTimeFormat.format(c.getCase().getAppointmentDate()));
				c.setBookingDate(dateFormat.format(c.getCase().getBookingDate()));
				c.setDeliverdDate(dateFormat.format(c.getCase().getDeliveredDate()));
				c.setPatientName(c.getCase().getPatient().getName());
				c.setVendorName(c.getCase().getVender().getName());
				c.setDoctorName(c.getCase().getDoctor().getName());
				c.setStatus(c.getCase().getStatus().toString());
				c.setCreatedBy(c.getCase().getCreatedBy());
				c.setActions(c.getCase().getnextActions());
				c.setCrownDetails(c.getCase().getCrown().toString());
				c.setCase(null);
			});
			response.setSuccess(true);
		    response.setData(cases);	
		}
		catch (Exception e) {
			response.setSuccess(false);
		}
		
		return response;
	}
	
	public AppResponse<Map<String, CaseStatus>> changeCaseStatus(Map<String, CaseStatus> cases){
		
		AppResponse<Map<String, CaseStatus>> response = new AppResponse<>();
		Map<String, CaseStatus> failed = new HashMap<>();
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			
			cases.forEach((key,value) ->{
				List<CaseSearchResult> c = caseRepo.findLatestCase(key);
				if(c.size() >0) {
					if(c.get(0).getCase().getStatus() == value || !c.get(0).getCase().getnextActions().contains(value)){
						failed.put(key, value);
					}
					else {
						Case report = c.get(0).getCase();
						report.setId(null);
						report.setStatus(value);
						report.setUpdateBy(userId);
						report.setUpdateDate(Config.fomatDate(new Date()));
						report.setVersion(report.getVersion()+1);
						caseRepo.save(report);
					}
				}
			 
			});
			if(failed.size()>0) {
			response.setSuccess(false);
			response.setData(failed);
			response.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
			}
			else {
			response.setSuccess(true);
			response.setMsg(Arrays.asList(StringConstant.DONE));
			}
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
			
		}
		return response;
		
	}

}
