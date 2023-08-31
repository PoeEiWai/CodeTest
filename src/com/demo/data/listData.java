package com.demo.data;

import java.util.ArrayList;

public class listData {

	private String status_message="";
	private String date_time="";
	private ArrayList<billrequest> billers=new ArrayList<>();
	public String getStatus_message() {
		return status_message;
	}
	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public ArrayList<billrequest> getBillers() {
		return billers;
	}
	public void setBillers(ArrayList<billrequest> billers) {
		this.billers = billers;
	}
	
			
}
