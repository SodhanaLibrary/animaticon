package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.SessionDTO;

public class UsersDAO {

	public static SessionDTO getSessionData(String email) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		SessionDTO pojo = new SessionDTO();
		try {
			conn = DBConn.getConnection();
			ps = conn.prepareStatement(
					"select DISTINCT(entitlement) from role_entitlements where role in(select role from user_roles where user_id in (select uuid from users where email = ? ));");
			ps.setString(1, email);
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					pojo.addEntitlement(res.getString("entitlement"));
				}
			}
			DBConn.close(conn, ps, res);
		} catch (ClassNotFoundException | SQLException e) {
			DBConn.close(conn, ps, res);
			System.out.println(e.getMessage());
			throw new DBException("Excepion while accessing database");
		}
		return pojo;
	}

}
