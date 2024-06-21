package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tabling.dto.LocationDTO;
import tabling.dto.MenuDTO;
import tabling.util.DBConnectionManager;

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
	
	public List<MenuDTO> getMenuByRestaurantId(int restaurtantId) throws SQLException {
		List<MenuDTO> list = new ArrayList<>();
		String query = " select * from menu where restaurant_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurtantId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MenuDTO dto = new MenuDTO().builder()
						.restaurantId(rs.getInt("restaurant_id"))
						.foodId(rs.getInt("food_id"))
						.price(rs.getInt("price"))
						.build();
				list.add(dto);						
			}			
		}
		return list;
	}
	
}
