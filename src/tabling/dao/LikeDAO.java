package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.util.DBConnectionManager;

public class LikeDAO {

	public boolean readLike(int customerId, int restaurantId) throws SQLException {
		String query = " select * from likes where customer_id = ? and restaurant_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	public int getLike(int customerId, int restaurantId) throws SQLException {
		String query = " insert into likes values(?, ?)  ";
		int likeCount = 0;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			likeCount = pstmt.executeUpdate();
			
		}
		return likeCount;
	}
	
	
	
	public int getUnlike(int customerId, int restaurantId) throws SQLException {
		String query = " delete from likes where customer_id = ? and restaurant_id = ? ";
		int unlikeCount = 0;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			unlikeCount = pstmt.executeUpdate();
		}
		return unlikeCount;
	}
	
	public String getLikeCount(int restaurantId) throws SQLException {
		String query = " select count(*) from likes where restaurant_id = ? ";
		String result = null;
		try(Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);
			try(ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					result = rs.getString(restaurantId);
				}
			}
		}
		return result;
	}
	
}
