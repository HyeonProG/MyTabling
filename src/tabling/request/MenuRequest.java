package tabling.request;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tabling.dto.MenuDTO;

public class MenuRequest {
	private String urlStr;
	private Gson gson;

	public MenuRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/menu";
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	// 해당 음식의 이름 요청
	public String getFoodName(int foodId) {
		String str = null;
		try {
			String selectUrl = urlStr + "/food/" + "foodId=" + String.valueOf(foodId) + "&";
			str = Request.getRequest(selectUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 해당 식당의 모든 메뉴 리스트 요청
	public List<MenuDTO> getMenuByRestaurantId(int restaurantId) {
		List<MenuDTO> list = new ArrayList<>();
		try {
			String selectUrl = urlStr + "/restaurant/" + "restaurantId=" + String.valueOf(restaurantId);
			String str = Request.getRequest(selectUrl);
			Type dtoType = new TypeToken<List<MenuDTO>>() {
			}.getType();
			list = gson.fromJson(str, dtoType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
