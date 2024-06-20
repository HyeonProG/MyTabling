package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import tabling.dto.ReservationDTO;
import tabling.dto.RestaurantDTO;
import tabling.util.DBConnectionManager;

public class ReservationDAO {

	
public int checkReservation(int restaurantId,int reservationId) throws SQLException {
		int count=0;

		String query = " select count(*) from reservation\r\n"
				+ "where reservation_state='Y' and restaurant_id=? and reservation_id<=? ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);
			pstmt.setInt(2, reservationId);
			ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					count=rs.getInt("count(*)");
//					System.out.println("대기 "+ count+"번 차례 입니다");
					}
				
		}catch (SQLException e) {
				e.printStackTrace();
			}
		return count;
	}
	

public List<ReservationDTO> getReservationByCustomer(int customerId) throws SQLException {
	List<ReservationDTO> list = new ArrayList<>();
	String query = " select * from reservation where customer_id=? and reservation_state='Y'  ";
	try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, customerId);
		try (ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				ReservationDTO dto = new ReservationDTO().builder() //
						.reservationId(rs.getInt(1)) //
						.reservationState(rs.getString(2)) //
						.reservationTime(rs.getString(3)) //
						.customerId(rs.getInt(4)) //
						.restaurantId(rs.getInt(5)) //
						.build();
				list.add(dto);
			}
		}
	}
	return list;
}	
	
//public static void main(String[] args) {
//	
//	ReservationDAO dao= new ReservationDAO();
//	try {
//		dao.checkReservation(55, 13);
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//}

	
}
