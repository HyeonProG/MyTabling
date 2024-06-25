package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class CustomerReservationDAO {

	/**
	 * 예약하기 <BR>
	 * 예약 테이블에 INSERT, 고객 예약 상태 UPDATE 동시 진행
	 */
	public void reservation(int customerId, int restaurantId) throws SQLException {

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String queryInsert = Define.INSERT_RESERVATION;
			PreparedStatement pstmt = conn.prepareStatement(queryInsert);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();

			String queryUpdate = Define.UPDATE_CUSTOMER_STATE_Y;
			PreparedStatement pstmt2 = conn.prepareStatement(queryUpdate);
			pstmt2.setInt(1, customerId);
			pstmt2.executeUpdate();

			conn.commit();

		}
	}

	/**
	 * 예약이행 or 취소 <BR>
	 * 예약 테이블, 고객 테이블 예약 상태 UPDATE 동시 진행
	 */
	public void cancel(int customerId, int restaurantId) throws SQLException {

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String queryReservation = Define.UPDATE_RESERVATION_STATE_N;
			PreparedStatement pstmt = conn.prepareStatement(queryReservation);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();

			String queryCustomer = Define.UPDATE_CUSTOMER_STATE_N;
			PreparedStatement pstmt2 = conn.prepareStatement(queryCustomer);
			pstmt2.setInt(1, customerId);
			pstmt2.executeUpdate();

			conn.commit();
		}
	}
}