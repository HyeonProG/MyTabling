package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tabling.dto.CustomerDTO;
import tabling.util.DBConnectionManager;

public class CustomerDAO {

	public void addUser(String name, String phone, int local) throws SQLException {
		String query = " INSERT INTO customer(customer_name,phone,location_id) VALUES(?, ?, ?) ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setInt(3, local);
			pstmt.executeUpdate();
			System.out.println("UserDAOImpl : 데이터베이스에 입력하기 성공");
		}
	}

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

	public CustomerDTO authenticatePhone(String phone) {
		CustomerDTO dto;
		String query = "  SELECT * FROM customer WHERE phone = ?  ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phone);
			ResultSet rs = pstmt.executeQuery();

			// result = rs.next();
			if (rs.next()) {
				dto = new CustomerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
				return dto;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean authenticateAll(String name, String phone) throws SQLException {
		
		String query = "  SELECT customer_name FROM customer WHERE phone = ?  ";
		boolean result = false;

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phone);
			ResultSet rs = pstmt.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	// TODO - 회원정보 수정/ 파라미터값으로 DTO를 받아와야할지
	public void updateCustomer(String name, int location, String phone) throws SQLException{
		String query = "  UPDATE customer SET customer_name = ?, location_id = ? WHERE phone = ?  ";
		
		try (Connection conn = DBConnectionManager.getInstance().getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1,name);
			pstmt.setInt(2,location);
			pstmt.setString(3, phone);
			pstmt.executeUpdate();
		}
		
		// throws SQLException 해서 catch 삭제
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
