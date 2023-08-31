package com.demo.data;

public class transcationData {
	private int id = 0;
	private String api_caller = "";
	private int bill_id = 0;
	private int amount = 0;
	private String reference_no = "";
	private String phone_number = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApi_caller() {
		return api_caller;
	}

	public void setApi_caller(String api_caller) {
		this.api_caller = api_caller;
	}

	public int getBill_id() {
		return bill_id;
	}

	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getReference_no() {
		return reference_no;
	}

	public void setReference_no(String reference_no) {
		this.reference_no = reference_no;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

}
