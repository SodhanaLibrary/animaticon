package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.model.AnimaticonBannerDTO;

public class AnimaticonBannerDAO {

	public static ArrayList<AnimaticonBannerDTO> getAnimaticonBanner(String uuid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		AnimaticonBannerDTO pojo = null;
		ArrayList<AnimaticonBannerDTO> alc = new ArrayList<AnimaticonBannerDTO>();
		try {
			conn = DBConn.getConnection();
			ps = conn.prepareStatement(
					"select uuid, name, tags, animaticonBannerName, objJson from animaticon_banner where uuid = ?");
			ps.setString(1, uuid);
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					pojo = new AnimaticonBannerDTO();
					pojo.setUuid(res.getString("uuid"));
					pojo.setName(res.getString("name"));
					pojo.setTags(res.getString("tags"));
					pojo.setObjJson(res.getString("objJson"));
					pojo.setAnimaticonBannerName(res.getString("animaticonBannerName"));
					alc.add(pojo);
				}
			}
			DBConn.close(conn, ps, res);
		} catch (ClassNotFoundException | SQLException e) {
			DBConn.close(conn, ps, res);
			System.out.println(e.getMessage());
			throw new DBException("Excepion while accessing database");
		}
		return alc;
	}

	public static AnimaticonBannerDTO addAnimaticonObjSet(AnimaticonBannerDTO pojo) throws DBException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet res = null;
		UUID uuid = UUID.randomUUID();
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement(
					"insert into animaticon_banner (uuid,name,tags,animaticonBannerName,objJson) values (?,?,?,?,?)");
			ps1.setString(1, uuid.toString());
			ps1.setString(2, pojo.getName());
			ps1.setString(3, pojo.getTags());
			ps1.setString(4, pojo.getAnimaticonBannerName());
			ps1.setString(5, pojo.getObjJson());
			ps1.executeUpdate();
			conn.commit();
			DBConn.close(conn, ps1, res);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			DBConn.close(conn, ps1, res);
			throw new DBException("Excepion while accessing database");
		}
		pojo.setUuid(uuid.toString());
		return pojo;
	}

	public static void deleteAnimaticonObjSet(AnimaticonBannerDTO aset) throws DBException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet res = null;
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement("delete from animaticon_banner where uuid = ?");
			ps1.setString(1, aset.getUuid());
			ps1.executeUpdate();
			conn.commit();
			DBConn.close(conn, ps1, res);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			DBConn.close(conn, ps1, res);
			throw new DBException("Excepion while accessing database");
		}
	}

	public static ArrayList<AnimaticonBannerDTO> filterAnimaticonBanner(String q) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		AnimaticonBannerDTO pojo = null;
		ArrayList<AnimaticonBannerDTO> alc = new ArrayList<AnimaticonBannerDTO>();
		try {
			conn = DBConn.getConnection();
			ps = conn.prepareStatement(
					"select uuid, name, tags, animaticonBannerName, objJson from animaticon_banner where tags like ?");
			if (q != null) {
				ps.setString(1, "%" + q + "%");
			} else {
				ps.setString(1, "");
			}
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					pojo = new AnimaticonBannerDTO();
					pojo.setUuid(res.getString("uuid"));
					pojo.setName(res.getString("name"));
					pojo.setTags(res.getString("tags"));
					pojo.setObjJson(res.getString("objJson"));
					pojo.setAnimaticonBannerName(res.getString("animaticonBannerName"));
					alc.add(pojo);
				}
			}
			DBConn.close(conn, ps, res);
		} catch (ClassNotFoundException | SQLException e) {
			DBConn.close(conn, ps, res);
			System.out.println(e.getMessage());
			throw new DBException("Excepion while accessing database");
		}
		return alc;
	}

	public static void updateAnimaticonObjSet(AnimaticonBannerDTO pojo) throws DBException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet res = null;
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement(
					"update animaticon_banner set name = ?,tags = ?,animaticonBannerName = ?,objJson = ? where uuid = ?");
			ps1.setString(1, pojo.getName());
			ps1.setString(2, pojo.getTags());
			ps1.setString(3, pojo.getAnimaticonBannerName());
			ps1.setString(4, pojo.getObjJson());
			ps1.setString(5, pojo.getUuid());
			ps1.executeUpdate();
			conn.commit();
			DBConn.close(conn, ps1, res);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			DBConn.close(conn, ps1, res);
			throw new DBException("Excepion while accessing database");
		}
	}
}
