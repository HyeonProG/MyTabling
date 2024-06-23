package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.dto.ReservationDTO;
import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class ReservationDAO {

	// 한 식당에 대해서 나를 포함하여 나보다 앞선, 유효한 예약이 몇개인지 확인하는 메서드
	public int checkReservation(int restaurantId, int reservationId) throws SQLException {
		int count = 0;
		String query = Define.SELECT_RESERVATION_COUNT_BY_RESTID_AND_RESERID;

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);
			pstmt.setInt(2, reservationId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("count(*)");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	// 해당 고객의 예약 테이블을 확인하는 메서드
	public ReservationDTO getReservationByCustomer(int customerId) throws SQLException {
		ReservationDTO dto = null;
		String query = " SELECT * FROM reservation WHERE customer_id = ? and reservation_state = 'Y'  ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					dto = new ReservationDTO().builder() //
							.reservationId(rs.getInt(1)) //
							.reservationState(rs.getString(2)) //
							.reservationTime(rs.getString(3)) //
							.customerId(rs.getInt(4)) //
							.restaurantId(rs.getInt(5)) //
							.build();
				}
			}
		}
		return dto;
	}

}
