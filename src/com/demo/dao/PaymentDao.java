package com.demo.dao;

import com.demo.data.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class PaymentDao {
	public static String datetoString() {
		String l_date = "";
		java.util.Date l_Date = new java.util.Date();
		l_date = getStartZero(4, String.valueOf(l_Date.getYear() + 1900))
				+ getStartZero(2, String.valueOf(l_Date.getMonth() + 1))
				+ getStartZero(2, String.valueOf(l_Date.getDate())) + getStartZero(2, String.valueOf(l_Date.getHours()))
				+ getStartZero(2, String.valueOf(l_Date.getMinutes()))
				+ getStartZero(2, String.valueOf(l_Date.getSeconds()));

		return l_date;
	}

	public static String getStartZero(int aZeroCount, String aValue) {
		while (aValue.length() < aZeroCount) {
			aValue = "0" + aValue;
		}
		return aValue;
	}

	public static Result login(LoginData data, Connection conn) throws SQLException {
		Result result = new Result();
		try {
			String sql = "select id from user where recordstatus=1 and username=? and password=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, data.getUsername());
			stmt.setString(2, data.getPassword());
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				result.setState(true);
				result.setAccess_token(doGenerateToken());
				result.setStatus_message("Login is successful");
			} else {
				result.setState(false);
				result.setStatus_message("User doesn't exit!");
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static billresponse billing(billrequest data, Connection conn) throws SQLException {
		billresponse result = new billresponse();
		try {
			int id = getid("billing", conn);
			String today = datetoString();
			String sql = "insert into billing(id,createdDate,recordstatus,name,description) values (?,?,1,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setString(2, today);
			stmt.setString(3, data.getName());
			stmt.setString(4, data.getDescription());
			// ResultSet res = stmt.executeQuery();
			if (stmt.executeUpdate() > 0) {
				result.setState(true);
				result.setStatus_message("Bill Top up is successfully saved in the system");
				result.setBill_id(id);
				result.setDescription(data.getDescription());
				result.setName(data.getName());
				result.setDate_time(today);
			} else {
				result.setState(false);
				result.setStatus_message("Billing Fail !");
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static int getid(String table, Connection conn) throws SQLException {
		int id = 1;
		try {
			String sql = "select max(id) as num from  " + table;
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				id = res.getInt("num") + 1;
			}

		} catch (Exception e) {

		}
		return id;
	}

	public static transcationResponse transcation(transcationData data, Connection conn) throws SQLException {
		transcationResponse result = new transcationResponse();
		try {
			if (checkRef_no(data.getReference_no(), conn)) {
				int id = getid("transcation", conn);
				String today = datetoString();
				String sql = "insert into transcation(id,createdDate,recordstatus,api_caller,bill_id,Amount,reference_no,phone_number) values (?,?,1,?,?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, id);
				stmt.setString(2, today);
				stmt.setString(3, data.getApi_caller());
				stmt.setInt(4, data.getBill_id());
				stmt.setInt(5, data.getAmount());
				stmt.setString(6, data.getReference_no());
				stmt.setString(7, data.getPhone_number());

				// ResultSet res = stmt.executeQuery();
				if (stmt.executeUpdate() > 0) {

					result.setStatus_message("Transaction is successful!");
					result.setAmount(data.getAmount());
					result.setTransaction_id(id);
					result.setPhone_number(data.getPhone_number());
					result.setTransaction_date(today);
				} else {
					result.setStatus_message("Transcation Fail !");
				}
			} else {
				result.setStatus_message("Reference_no already exit !");
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static boolean checkRef_no(String ref, Connection conn) throws SQLException {
		boolean state = true;
		try {
			String sql = "select id from  transcation where recordstatus=1 and reference_no=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, ref);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				state = false;
			}

		} catch (Exception e) {

		}
		return state;
	}

	public static listData list(int id, Connection conn) throws SQLException {
		listData data = new listData();
		ArrayList<billrequest> list = new ArrayList<>();
		try {
			String sql = "select *  from  billing where recordstatus=1 and id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				billrequest obj = new billrequest();
				data.setStatus_message("Transaction is successful!");
				data.setDate_time(res.getString("createdDate"));
				obj.setBill_id(res.getInt("id"));
				obj.setName(res.getString("name"));
				obj.setDescription(res.getString("description"));
				list.add(obj);
				data.setBillers(list);
			} else {
				data.setStatus_message("Error!");
			}

		} catch (Exception e) {

		}
		return data;
	}

	public static transcationData transcationlist(int id, Connection conn) throws SQLException {
		transcationData data = new transcationData();
		ArrayList<billrequest> list = new ArrayList<>();
		try {
			String sql = "select *  from  transcation where recordstatus=1 and id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				data.setAmount(res.getInt("amount"));
				data.setApi_caller(res.getString("api_caller"));
				data.setId(res.getInt("id"));
				data.setPhone_number(res.getString("phone_number"));
				data.setReference_no(res.getString("reference_number"));
			} else {

			}

		} catch (Exception e) {

		}
		return data;
	}

	private static String doGenerateToken() {
		long expire = (long) (3 * 60 * 1000);
		Date exptime = new Date(System.currentTimeMillis() + expire);
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("HwPV+v0uOs0=");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(exptime)
				.signWith(signatureAlgorithm, signingKey).compact();

	}
}
