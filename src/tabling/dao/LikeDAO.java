package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.dto.LikeDTO;
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
	
	public void getLike(int customerId, int restaurantId) throws SQLException {
		String query = " insert into likes values(?, ?)  ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();

		}
	}
	
	
	
	public void getUnlike(int customerId, int restaurantId) throws SQLException {
		String query = " delete from likes where customer_id = ? and restaurant_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();
		}
	}
	
}
