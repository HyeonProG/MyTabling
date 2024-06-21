package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import tabling.dto.RestaurantDTO;
import tabling.util.DBConnectionManager;
import tabling.util.Time;

@Data
public class RestaurantDAO {

	private boolean openFilter;
	private boolean likeFilter;
	private Time currentTime;

	public List<RestaurantDTO> getAllRestaurants(int customerId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = " SELECT * FROM restaurant ORDER BY CASE WHEN location_id = (SELECT location_id FROM customer WHERE customer_id = ? ) THEN 0 ELSE 1 END ";
		if (likeFilter) {
			query = " SELECT * FROM restaurant WHERE restaurant_id IN (SELECT restaurant_id FROM likes WHERE customer_id = ?) ORDER BY CASE WHEN location_id = (SELECT location_id FROM customer WHERE customer_id = ? ) THEN 0 ELSE 1 END ";
		}
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			if (likeFilter) {
				pstmt.setInt(2, customerId);
			}
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
							.restDay(rs.getString(9)) //
							.locationId(rs.getInt(10)) //
							.categoryId(rs.getInt(11)) //
							.build();
					if (openFilter) {
						// 영업중 일때만 리스트에 add함
						if (currentTime.isOpen(dto)) {
							list.add(dto);
						}
					} else {
						list.add(dto);
					}
				}
			}
		}
		return list;
	}

	public List<RestaurantDTO> getRestaurantsByCategory(int categoryId, int customerId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = " SELECT * FROM restaurant where category_id = ? ";
		if (likeFilter) {
			query = " SELECT * FROM restaurant where category_id = ? AND restaurant_id IN (SELECT restaurant_id FROM likes WHERE customer_id = ?); ";
		}
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, categoryId);
			if (likeFilter) {
				pstmt.setInt(2, customerId);
			}
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
							.restDay(rs.getString(9)) //
							.locationId(rs.getInt(10)) //
							.categoryId(rs.getInt(11)) //
							.build();
					if (openFilter) {
						// 영업중 일때만 리스트에 add함
						if (currentTime.isOpen(dto)) {
							list.add(dto);
						}
					} else {
						list.add(dto);
					}
				}
			}
		}
		return list;

	}

	public List<RestaurantDTO> getRestaurantsByLocation(int locationId, int customerId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = " SELECT * FROM restaurant where location_id = ? ";
		if (likeFilter) {
			query = " SELECT * FROM restaurant where location_id = ? AND restaurant_id IN (SELECT restaurant_id FROM likes WHERE customer_id = ?); ";
		}
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, locationId);
			if (likeFilter) {
				pstmt.setInt(2, customerId);
			}
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
							.restDay(rs.getString(9)) //
							.locationId(rs.getInt(10)) //
							.categoryId(rs.getInt(11)) //
							.build();
					if (openFilter) {
						// 영업중 일때만 리스트에 add함
						if (currentTime.isOpen(dto)) {
							list.add(dto);
						}
					} else {
						list.add(dto);
					}
				}
			}
		}
		return list;

	}

	public RestaurantDTO authenticateOwnerId(int restaurantId) {
		RestaurantDTO dto;
		String query = "  SELECT * FROM restaurant WHERE restaurant_id = ?  ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new RestaurantDTO().builder() //
						.restaurantId(rs.getInt(1)) //
						.restaurantName(rs.getString(2)) //
						.phone(rs.getString(3)) //
						.address(rs.getString(4)) //
						.content(rs.getString(5)) //
						.openTime(rs.getString(6)) //
						.closeTime(rs.getString(7)) //
						.rating(rs.getDouble(8)) //
						.restDay(rs.getString(9)) //
						.locationId(rs.getInt(10)) //
						.categoryId(rs.getInt(11)) //
						.build();
				return dto;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
