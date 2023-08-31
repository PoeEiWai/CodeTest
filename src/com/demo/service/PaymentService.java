package com.demo.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.demo.data.LoginData;
import com.demo.data.Result;
import com.demo.data.*;
import com.demo.mgr.*;
@Path("/payment")
public class PaymentService {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Result login(LoginData data) {
		Result res= new Result();
		res=PaymentMgr.login(data);
		return res;
		
	}
	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public billresponse billing(billrequest data) {
		billresponse res= new billresponse();
		res=PaymentMgr.billing(data);
		return res;
		
	}
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public listData getlist() {
		listData res = new listData();
		int key = Integer.parseInt(request.getParameter("id"));
		if (key !=0 ) {
			res = PaymentMgr.list(key);
		} else {
			res.setStatus_message("Can't get Data!");
		}
		return res;
	}

	@POST
	@Path("pay")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public transcationResponse transcation(transcationData data) {
		transcationResponse res= new transcationResponse();
		if(data.getAmount()!=0 && !data.getApi_caller().equalsIgnoreCase("") && data.getBill_id()!=0
				&& !data.getPhone_number() .equalsIgnoreCase("") && !data.getReference_no().equalsIgnoreCase("")){
		if(	data.getAmount()<=100000 && data.getAmount()>0){
			if(data.getPhone_number().startsWith("+959") || data.getPhone_number().startsWith("959")){
			res=PaymentMgr.transcation(data);
			}else{
				res.setStatus_message("Please add valid phone number!");
			}
		}else{
			res.setStatus_message("Please add valid amount!");
		}
		}else{
			res.setStatus_message("Please fill necessary data!");
		}
		return res;
		
	}
	
	@GET
	@Path("transcation")
	@Produces(MediaType.APPLICATION_JSON)
	public transcationData gettranscation() {
		transcationData res = new transcationData();
		int key = Integer.parseInt(request.getParameter("id"));
		if (key !=0 ) {
			res = PaymentMgr.transcationlist(key);
		}
		return res;
	}

}
