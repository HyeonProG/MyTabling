package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class LocationDAO {

	// 로케이션 id를 로케이션 이름으로 바꿔주는 메서드
	public String getLocationName(int locationId) throws SQLException {
		String name = null;
		String query = Define.SELECT_LOCATION_BY_LOCATIONNAME;
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
