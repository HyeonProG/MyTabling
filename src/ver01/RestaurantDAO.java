package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

	public List<RestaurantDTO> getAllRestaurants() throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = " SELECT * FROM restaurant ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					RestaurantDTO dto = new RestaurantDTO().builder() //
							.restaurantId(rs.getInt(1)) //
							.restaurantName(rs.getString(2)) //
							.phone(rs.getString(3)) //
							.address(rs.getString(4)) //
							.content(rs.getString(5)) //
							.openTime(rs.getString(6)) //
							.closeTime(rs.getString(7)) //
							.rating(rs.getDouble(8)) //
							.likes(rs.getInt(9)) //
							.restDay(rs.getString(10)) //
							.locationId(rs.getInt(11)) //
							.categoryId(rs.getInt(12)) //
							.build();
					list.add(dto);
				}
			}
		}
		return list;
	}

	public List<RestaurantDTO> getRestaurantsByCategory(int categoryId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = " SELECT * FROM restaurant where category_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, categoryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					RestaurantDTO dto = new RestaurantDTO().builder() //
							.restaurantId(rs.getInt(1)) //
							.restaurantName(rs.getString(2)) //
							.phone(rs.getString(3)) //
							.address(rs.getString(4)) //
							.content(rs.getString(5)) //
							.openTime(rs.getString(6)) //
							.closeTime(rs.getString(7)) //
							.rating(rs.getDouble(8)) //
							.likes(rs.getInt(9)) //
							.restDay(rs.getString(10)) //
							.locationId(rs.getInt(11)) //
							.categoryId(rs.getInt(12)) //
							.build();
					list.add(dto);
				}
			}
		}
		return list;
	
	}

	public List<RestaurantDTO> getRestaurantsByLocation(int locationId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = " SELECT * FROM restaurant where location_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, locationId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					RestaurantDTO dto = new RestaurantDTO().builder() //
							.restaurantId(rs.getInt(1)) //
							.restaurantName(rs.getString(2)) //
							.phone(rs.getString(3)) //
							.address(rs.getString(4)) //
							.content(rs.getString(5)) //
							.openTime(rs.getString(6)) //
							.closeTime(rs.getString(7)) //
							.rating(rs.getDouble(8)) //
							.likes(rs.getInt(9)) //
							.restDay(rs.getString(10)) //
							.locationId(rs.getInt(11)) //
							.categoryId(rs.getInt(12)) //
							.build();
					list.add(dto);
				}
			}
		}
		return list;
	
	}
	
	// TODO 삭제 예정 테스트 코드
	public static void main(String[] args) {
		RestaurantDAO dao = new RestaurantDAO();
		try {
			for (RestaurantDTO dto : dao.getRestaurantsByLocation(7)) {
				System.out.println(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
