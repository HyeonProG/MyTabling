package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class restaurant_reservationDAO {

	// 식당에서 Table select * where 식당
	public List<ReservationDTO> selectReservation(int restaurantId) throws SQLException {
		
		List<ReservationDTO> list = new ArrayList<>();

		String query = " select * from reservation where restaurant_id = ? and reservation_state = 'Y' ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReservationDTO dto = new ReservationDTO()
							.builder()
							.reservationId(rs.getInt("reservation_id"))
							.reservationState(rs.getString("reservation_state"))
							.reservationTime(rs.getString("reservation_time"))
							.customerId(rs.getInt("customer_id"))
							.restaurantId(rs.getInt("restaurant_id"))
							.build();
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
		
		try(
				Connection conn = DBConnectionManager.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, restaurantId);
			pstmt.setInt(2, customerId);
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// TODO : TEST CODE
	public static void main(String[] args) {
		restaurant_reservationDAO dao = new restaurant_reservationDAO();
		
		try {
			dao.updateReservation(3,3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		restaurant_reservationDAO dao1 = new restaurant_reservationDAO();
		try {
			dao1.selectReservation(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
} // end of class
