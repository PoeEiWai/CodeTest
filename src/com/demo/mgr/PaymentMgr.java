package com.demo.mgr;

import java.sql.SQLException;

import com.demo.dao.PaymentDao;
import com.demo.data.*;
import java.sql.Connection;

public class PaymentMgr {

	public static Result login(LoginData pdata) {

		Result res = new Result();
		try (Connection conn = conManager.getConnection()) {
			if (conn != null) {

				res = PaymentDao.login(pdata, conn);
			} else {
				res.setStatus_message("Can't connect to Database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static billresponse billing(billrequest pdata) {
		billresponse res = new billresponse();
		try(Connection conn = conManager.getConnection()) {
			if (conn != null) {
				res = PaymentDao.billing(pdata, conn);
			} else {
				res.setStatus_message("Can't connect to Database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static listData list(int id) {
		listData res = new listData();
		try(Connection conn = conManager.getConnection()) {
			
			if (conn != null) {
				res = PaymentDao.list(id, conn);
			} else {
				res.setStatus_message("Can't connect to Database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static transcationData transcationlist(int id) {
		transcationData res = new transcationData();
		try(Connection conn = conManager.getConnection()) {
			
			if (conn != null) {
				res = PaymentDao.transcationlist(id, conn);
			} else {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	public static transcationResponse transcation(transcationData pdata) {
		transcationResponse res = new transcationResponse();
		try(Connection conn = conManager.getConnection()) {
			if (conn != null) {
				res = PaymentDao.transcation(pdata, conn);
			} else {
				res.setStatus_message("Can't connect to Database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
