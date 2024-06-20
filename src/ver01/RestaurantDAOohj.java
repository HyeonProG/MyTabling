package ver01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantDAOohj {

	public RestaurantDTO authenticateOwnerId(int restaurantId) {
		RestaurantDTO dto;
		String query = "  SELECT * FROM restaurant WHERE restaurant_id = ?  ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new RestaurantDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getInt(9),
						rs.getString(10), rs.getInt(11), rs.getInt(12));

				return dto;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
