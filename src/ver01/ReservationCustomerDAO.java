package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationCustomerDAO {

	// 예약하기(Reservation)
	public void addReservation(int customerId, int restaurantId) throws SQLException {
		String query = " insert into reservation(reservation_state,reservation_time,customer_id,restaurant_id)\r\n"
				+ "values ('Y',current_timestamp(),?,?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void updateCustomerReservationY(int customerId) throws SQLException {
		String query = " update customer set  state='Y' where customer_id=?   ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
		}
	}

	// 예약취소

	public void updateReservation(int customerId, int restaurantId) throws SQLException {
		String query = " update reservation set  state='N' where customer_id=? and restaurant_id=? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(2, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();
		}
	}

	public void updateCustomerReservationN(int customerId) throws SQLException {
		String query = " update reservation set  state='N' where customer_id=?   ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
		}
	}

}