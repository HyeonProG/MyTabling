package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ver01.DBConnectionManager;
import ver01.LocationDTO;

public class MenuDAO {

	public String getMenuName(int menuId) throws SQLException {
		String name = null;
		String query = " SELECT * FROM food WHERE food_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, menuId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					name = rs.getString("food_name");
				}
			}
		}
		return name;
	}
}
