package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tabling.dto.CustomerDTO;
import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class CustomerDAO {

	// 회원가입 메서드
	public void addCustomer(String name, String phone, int locationId) throws SQLException {
		
		String query = " INSERT INTO customer(customer_name, phone, location_id) VALUES(?, ?, ?) ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setInt(3, locationId);
			pstmt.executeUpdate();
		}
	}
	
	// TODO 최종에 안쓰면 지워야됨
	public List<CustomerDTO> getAllUsers() throws SQLException {

		List<CustomerDTO> list = new ArrayList<>();

		String query = "  SELECT * FROM customer  ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerDTO dto = new CustomerDTO().builder().customerId(rs.getInt("customer_id"))
						.customerName(rs.getString("customer_name")).phone(rs.getString("phone"))
						.state(rs.getString("state")).locationId(rs.getInt("location_Id")).build();
				list.add(dto);
			}

			for (CustomerDTO customerDTO : list) {
				System.out.println(customerDTO);
			}

		}

		return list;
	}

	// 전화번호 중복 확인 메서드
	public CustomerDTO authenticatePhone(String phone) throws SQLException {
		CustomerDTO dto;
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
				return dto;
			}
			
		}

		return null;
	}

//	public boolean authenticateAll(String name, String phone) throws SQLException {
//		
//		String query = "  SELECT customer_name FROM customer WHERE phone = ?  ";
//		boolean result = false;
//
//		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
//			PreparedStatement pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, phone);
//			ResultSet rs = pstmt.executeQuery();
//
//			result = rs.next();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return result;
//
//	}
	
	public void updateCustomer(String name, int location, String phone) throws SQLException{
		String query = " UPDATE customer SET customer_name = ?, location_id = ? WHERE phone = ? ";
		
		try (Connection conn = DBConnectionManager.getInstance().getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1,name);
			pstmt.setInt(2,location);
			pstmt.setString(3, phone);
			pstmt.executeUpdate();
		}
		
	}
	
	// TODO - 회원정보 삭제
	public void deleteCustomer(String phone)throws SQLException{
		String query = "  DELECT FROM customer WHERE phone = ?  ";
	
		try (Connection conn = DBConnectionManager.getInstance().getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phone);
			pstmt.executeUpdate();
		}
		// throws SQLException 해서 catch 삭제
	}

}
