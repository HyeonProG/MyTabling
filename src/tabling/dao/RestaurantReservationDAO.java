package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import tabling.dto.ReservationDTO;
import tabling.dto.ReservationDTO2;
import tabling.util.DBConnectionManager;

public class RestaurantReservationDAO {

	// 식당에서 Table select * where 식당
	public List<ReservationDTO> selectReservation(int restaurantId) throws SQLException {

		List<ReservationDTO> list = new ArrayList<>();

		String query = " select * from reservation where restaurant_id = ? and reservation_state = 'Y' ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReservationDTO dto = new ReservationDTO().builder().reservationId(rs.getInt("reservation_id"))
							.reservationState(rs.getString("reservation_state"))
							.reservationTime(rs.getString("reservation_time")).customerId(rs.getInt("customer_id"))
							.restaurantId(rs.getInt("restaurant_id")).build();
					list.add(dto);
				}
				for (ReservationDTO reservationDTO : list) {
					System.out.println(reservationDTO);
				}
			}
		}
		return list;
	} // end of selectReservation

	// 식당에서 예약 이행
	public void updateReservation(int restaurantId, int customerId) throws SQLException {

		String query = "UPDATE reservation SET reservation_state = 'N' WHERE restaurant_id = ? and "
				+ " customer_id = ? ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, restaurantId);
			pstmt.setInt(2, customerId);
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// TODO : TEST CODE
//	public static void main(String[] args) {
//		RestaurantReservationDAO dao = new RestaurantReservationDAO();
//
//		try {
//			dao.updateReservation(3, 3);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		RestaurantReservationDAO dao1 = new RestaurantReservationDAO();
//		try {
//			dao1.selectReservation(1);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	// TODO - 점주측에서 예약자 명단 띄우기 위함
	public List<ReservationDTO2> costomerList(int restId) throws SQLException {

		List<ReservationDTO2> list = new ArrayList<>();

		// JOIN으로 필요한 것만 띄우기 - 고객이름, 전화번호, 예약시간, 예약상태
		String query = "  SELECT customer_name, c.phone,reservation_time,state FROM restaurant as rest JOIN reservation as rev on rest.restaurant_id = rev.restaurant_id JOIN customer as c on rev.customer_id = c.customer_id WHERE rest.restaurant_id = ?  ";
		
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restId);
			try (ResultSet rs = pstmt.executeQuery()){
				while (rs.next()) {
					ReservationDTO2 dto2 = new ReservationDTO2().builder().cName(rs.getString("customer_name"))
							.cPhone(rs.getString("phone")).revTime(rs.getString("reservation_time"))
							.state(rs.getString("state")).build();
					list.add(dto2);
				}
				
			}
		} 
		return list;

	};

} // end of class
