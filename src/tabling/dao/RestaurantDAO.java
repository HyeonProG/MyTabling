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
import tabling.util.Define;
import tabling.util.Time;

@Data
public class RestaurantDAO {

	private boolean openFilter; // 식당 리스트 프레임에서 영업중 필터가 걸리면 true
	private boolean likeFilter; // 식당 리스트 프레임에서 좋아요 필터가 걸리면 true
	private Time currentTime; // 영업중 필터가 걸릴때 시간을 받아와서 비교하기 위한 변수

	/**
	 * 모든 식당 리스트를 반환하는 메서드
	 * 
	 * @param customerId : 고객의 선호 지역, 좋아요한 식당을 조회하기 위함
	 */
	public List<RestaurantDTO> getAllRestaurants(int customerId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = Define.SELECT_RESTAURANT_ALL;
		// 좋아요 필터가 걸리면 쿼리문 변경
		if (likeFilter) {
			query = Define.SELECT_RESTAURANT_ALL_FILTERED;
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

	/**
	 * 해당 카테고리의 식당을 반환하는 메서드
	 * 
	 * @param categoryId
	 * @param customerId : 좋아요한 식당을 조회하기 위함
	 */
	public List<RestaurantDTO> getRestaurantsByCategory(int categoryId, int customerId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = Define.SELECT_RESTAURANT_BY_CATEGROY;
		if (likeFilter) {
			query = Define.SELECT_RESTAURANT_BY_CATEGROY_FILTERED;
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

	/**
	 * 해당 지역의 식당을 반환하는 메서드
	 * 
	 * @param locationId
	 * @param customerId : 좋아요한 식당을 조회하기 위함
	 */
	public List<RestaurantDTO> getRestaurantsByLocation(int locationId, int customerId) throws SQLException {
		List<RestaurantDTO> list = new ArrayList<>();
		String query = Define.SELECT_RESTAURANT_BY_LOCATION;
		if (likeFilter) {
			query = Define.SELECT_RESTAURANT_BY_LOCATION_FILTERED;
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

	public RestaurantDTO getRestaurantByRestaurantId(int restaurantId) {
		RestaurantDTO dto = null;
		String query = Define.SELECT_RESTAURANT_BY_RESTAURANTID;

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
		return dto;
	}

}
