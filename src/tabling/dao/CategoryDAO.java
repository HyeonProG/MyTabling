package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class CategoryDAO {
	
	// 카테고리 id를 이름으로 바꿔주는 메서드 
	public String getCategoryName(int categoryId) throws SQLException {
		String name = null;
		String query = Define.SELECT_CATEGORY_BY_CATEGORYID;
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
