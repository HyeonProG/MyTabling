package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.dto.LikeDTO;
import tabling.util.DBConnectionManager;

public class LikeDAO {

	public LikeDTO getLike(int customerId, int restaurantId) throws SQLException {
		String query = " select * from likes where customer_id = ? and restaurant_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new LikeDTO(rs.getInt("customer_id"), rs.getInt("restaurant_id"));
				}
			}
		}
		return null;
	}
	
}
