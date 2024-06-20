package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.util.DBConnectionManager;

public class LocationDAO {

	public String getLocationName(int locationId) throws SQLException {
		String name = null;
		String query = " SELECT * FROM location WHERE location_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, locationId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					name = rs.getString("location_name");
				}
			}
		}
		return name;
	}
}
