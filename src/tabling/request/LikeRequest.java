package tabling.request;

import java.io.IOException;

import tabling.dto.JsonDTO;

public class LikeRequest {
	private String urlStr;

	public LikeRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/like";
	}

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
