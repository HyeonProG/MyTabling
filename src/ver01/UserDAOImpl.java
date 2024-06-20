package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

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

	@Override
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

	@Override
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

	@Override
	public void addUser(CustomerDTO dto) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
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

}
