package tabling.request;

import java.io.IOException;

import tabling.dto.JsonDTO;

public class LikeRequest {
	private String urlStr;

	public LikeRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/like";
	}

	// 좋아요 요청
	public int addLike(int customerId, int restaurantId) {
		int likeCount = 0;
		try {
			String insertUrl = urlStr + "/insert";
			JsonDTO dto = new JsonDTO(customerId, restaurantId);
			String str = Request.postRequest(insertUrl, dto);
			System.out.println(str);
			likeCount = Integer.parseInt(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return likeCount;
	}

	// 해당 고객의 해당 식당에 대한 좋아요 여부 확인 요청
	public boolean getLike(int customerId, int restaurantId) {
		boolean like = false;
		try {
			String selectUrl = urlStr + "/getLike/" + "customerId=" + String.valueOf(customerId) + "&" + "restaurantId="
					+ String.valueOf(restaurantId);
			String str = Request.getRequest(selectUrl);
			like = Boolean.parseBoolean(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return like;
	}

	// 좋아요 취소 요청
	public int deleteLike(int customerId, int restaurantId) {
		int likeCount = 0;
		try {
			String deleteUrl = urlStr + "/delete/" + "customerId=" + String.valueOf(customerId) + "&" + "restaurantId="
					+ String.valueOf(restaurantId);
			String str = Request.getRequest(deleteUrl);
			likeCount = Integer.parseInt(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return likeCount;
	}

	// 해당 식당의 총 좋아요 수 확인 요청
	public int getLikeCount(int restaurantId) {
		int likeCount = 0;
		try {
			String selectUrl = urlStr + "/getLikeCount/" + "customerId=" + String.valueOf(0) + "&" + "restaurantId="
					+ String.valueOf(restaurantId);
			String str = Request.getRequest(selectUrl);
			likeCount = Integer.parseInt(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return likeCount;
	}
}
