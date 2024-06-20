package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Restaurant_detailDAO {
	private static final Restaurant_detailDAO detailDAO = new Restaurant_detailDAO();
	
	// 식당 정보 디테일 조회 기능(식당ID로)
	public List<MenuDTO> getMenuById(int restaurtantId) throws SQLException {
		List<MenuDTO> list = new ArrayList<>();
		String query = " select * from menu where restaurant_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, 2);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MenuDTO dto = new MenuDTO().builder()
						.restaurantId(rs.getInt("restaurant_id"))
						.foodId(rs.getInt("food_id"))
						.price(rs.getInt("price"))
						.build();
				list.add(dto);						
			}			
		}
		return list;
	}
	
	public LikeDTO getLike(int customerId, int restaurantId) throws SQLException {
		String query = " select * from likes where customer_id = ? and restaurant_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new LikeDTO(rs.getInt("customer_id"), rs.getInt("restaurant_id"));
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		try {
			List<MenuDTO> list = detailDAO.getMenuById(2);
			System.out.println(list.size());
			System.out.println(list.toString());
			
			LikeDTO like = detailDAO.getLike(1, 1);
			System.out.println(like);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
