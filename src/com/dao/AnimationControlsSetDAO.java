package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.model.AnimationControlsSetDTO;

public class AnimationControlsSetDAO {
	
	public static ArrayList<AnimationControlsSetDTO> getAnimationControlsSets(String animaticonObjName) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet res=null;
		AnimationControlsSetDTO pojo = null;
		ArrayList<AnimationControlsSetDTO> alc = new ArrayList<AnimationControlsSetDTO>();
		try {
			conn = DBConn.getConnection();
			ps = conn.prepareStatement("select uuid, name, img, animaticonObjName, tags, objJson from animation_controls_set where animaticonObjName = ?");
			ps.setString(1, animaticonObjName);
			res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					pojo = new AnimationControlsSetDTO();
					pojo.setUuid(res.getString("uuid"));
					pojo.setTags(res.getString("tags"));
					pojo.setName(res.getString("name"));
					pojo.setImg(res.getString("img"));
					pojo.setObjJson(res.getString("objJson"));
					pojo.setAnimaticonObjName(res.getString("animaticonObjName"));
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
	
	
	public static String addAnimationControlSet(AnimationControlsSetDTO pojo) throws DBException{
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet res = null;
		UUID uuid = UUID.randomUUID();
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement("insert into animation_controls_set (uuid,name,img,animaticonObjName,tags,objJson) values (?,?,?,?,?,?)");
			ps1.setString(1,uuid.toString());
			ps1.setString(2,pojo.getName());
			ps1.setString(3,pojo.getImg());
			ps1.setString(4,pojo.getAnimaticonObjName());
			ps1.setString(5,pojo.getTags());
			ps1.setString(6,pojo.getObjJson());
			ps1.executeUpdate();
			conn.commit();
			DBConn.close(conn, ps1, res);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			DBConn.close(conn, ps1, res);
			throw new DBException("Excepion while accessing database");
		}
		return uuid.toString();
	}


	public static void deleteAnimationControlSet(AnimationControlsSetDTO aset) throws DBException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet res = null;
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement("delete from animation_controls_set where uuid = ?");
			ps1.setString(1,aset.getUuid());
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
