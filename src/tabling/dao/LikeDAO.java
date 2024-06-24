package tabling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tabling.util.DBConnectionManager;
import tabling.util.Define;

public class LikeDAO {

	// 식당 상세 페이지 오픈시 좋아요 누른 식당인지 확인
	public boolean getLike(int customerId, int restaurantId) throws SQLException {
		String query = Define.SELECT_LIKES_BY_CUSTOMER_AND_RESTAURANT;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	// 좋아요 테이블에 INSERT -> 해당 식당의 변경된 좋아요 수 받아옴
	public int addLike(int customerId, int restaurantId) throws SQLException {
		String query = Define.INSERT_LIKES;
		int likeCount = 0;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();
			likeCount = getLikeCount(restaurantId);
		}
		return likeCount;
	}

	// 좋아요 테이블에서 DELETE -> 해당 식당의 변경된 좋아요 수 받아옴
	public int deleteLike(int customerId, int restaurantId) throws SQLException {
		String query = Define.DELETE_LIKES;
		int unlikeCount = 0;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, restaurantId);
			pstmt.executeUpdate();
			unlikeCount = getLikeCount(restaurantId);
		}
		return unlikeCount;
	}

	// 좋아요 테이블에서 한 식당의 좋아요수 count
	public int getLikeCount(int restaurantId) throws SQLException {
		String query = Define.SELECT_LIKES_COUNT_BY_RESTAURANT;
		int result = 0;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					result = rs.getInt(1);
				}
			}
		}
		return result;
	}

}
