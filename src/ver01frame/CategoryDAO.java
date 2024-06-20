package ver01frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ver01.DBConnectionManager;

public class CategoryDAO {
	public String getCategoryName(int categoryId) throws SQLException {
		String name = null;
		String query = " SELECT * FROM category WHERE category_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, categoryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					name = rs.getString("category_name");
				}
			}
		}
		return name;
	}
}
