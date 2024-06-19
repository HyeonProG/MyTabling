package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class restaurant_detailDAO {
	private static final restaurant_detailDAO detailDAO = new restaurant_detailDAO();
	
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
	
	public static void main(String[] args) {
		
		try {
			List<MenuDTO> list = detailDAO.getMenuById(2);
			System.out.println(list.size());
			System.out.println(list.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
