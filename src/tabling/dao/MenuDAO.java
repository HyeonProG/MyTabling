package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tabling.dto.MenuDTO;
import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class MenuDAO {

	// 음식 id를 음식 이름으로 바꿔주는 메서드
	public String getFoodName(int foodId) throws SQLException {
		String name = null;
		String query = Define.SELECT_FOOD_BY_FOODID;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, foodId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					name = rs.getString("food_name");
				}
			}
		}
		return name;
	}

	// 해당 식당의 모든 메뉴를 반환하는 메서드
	public List<MenuDTO> getMenuByRestaurantId(int restaurantId) throws SQLException {
		List<MenuDTO> list = new ArrayList<>();
		String query = Define.SELECT_MENU_BY_RESTAURANTID;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				MenuDTO dto = new MenuDTO().builder().restaurantId(rs.getInt("restaurant_id")).foodId(rs.getInt("food_id")).price(rs.getInt("price"))
						.build();
				list.add(dto);
			}
		}
		return list;
	}

}
