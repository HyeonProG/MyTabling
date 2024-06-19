package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationCustomerDAO {
	// 예약하기(Reservation)
	public void addReservation(int customerId, int restaurantId) throws SQLException {
	customerId=1;
	restaurantId=1;
	String query = " insert into reservation(reservation_state,reservation_time,customer_id,restaurant_id)\r\n"
				+ "values ('Y',current_timestamp(),?,?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();
			System.out.println("완료1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateCustomerReservationY(int customerId) throws SQLException {
		customerId=1;
		String query = " update customer set  state='Y' where customer_id=?   ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
			System.out.println("완료2");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 예약취소

	public void updateReservation(int customerId, int restaurantId) throws SQLException {
		customerId=1;
		restaurantId=1;
		String query = " update reservation set  reservation_state='N' where customer_id=? and restaurant_id=? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();
			System.out.println("완료3");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCustomerReservationN(int customerId) throws SQLException {
		customerId=1;
		String query = " update customer set  state='N' where customer_id=?   ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareCall(query);
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
			System.out.println("완료4");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//임시
//	public static void main(String[] args) {
//	ReservationCustomerDAO dao=	new ReservationCustomerDAO();
//	try {
//		dao.addReservation(1, 1);
//		dao.updateCustomerReservationY(1);
//		dao.updateCustomerReservationN(1);
//		dao.updateReservation(1, 1);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	}

}