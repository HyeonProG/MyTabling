package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.dto.CustomerDTO;
import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class CustomerDAO {

	// 회원가입 메서드
	public void addCustomer(String name, String phone, int locationId) throws SQLException {

		String query = Define.INSERT_CUSTOMER;

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setInt(3, locationId);
			pstmt.executeUpdate();
		}
	}

	// 전화번호로 고객 정보를 얻는 메서드
	public CustomerDTO getCustomerByPhone(String phone) throws SQLException {
		CustomerDTO dto = null;
		String query = Define.SELECT_CUSTOMER_BY_PHONE;

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phone);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new CustomerDTO().builder() //
						.customerId(rs.getInt(1)) //
						.customerName(rs.getString(2)) //
						.phone(rs.getString(3)) //
						.state(rs.getString(4)) //
						.locationId(rs.getInt(5)) //
						.build();
			}
		}
		return dto;
	}

	// 회원 수정 시 호출 되는 메서드
	public void updateCustomer(String name, String phone, int locationId) throws SQLException {
		String query = Define.UPDATE_CUSTOMER_INFO;

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, locationId);
			pstmt.setString(3, phone);
			pstmt.executeUpdate();
		}

	}

	// 회원 탈퇴 메서드
	public void deleteCustomer(String phone) throws SQLException {
		String query = Define.DELETE_CUSTOMER;

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phone);
			pstmt.executeUpdate();
		}
	}

}
