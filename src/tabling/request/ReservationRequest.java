package tabling.request;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tabling.dto.JsonDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.ReservationForRestaurantDTO;

public class ReservationRequest {
	private String urlStr;
	private Gson gson;

	public ReservationRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/reservation";
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public void reservation(int customerId, int restaurantId) {
		try {
			String reservationUrl = urlStr + "/reservation";
			JsonDTO dto = new JsonDTO(customerId, restaurantId);
			String str = Request.postRequest(reservationUrl, dto);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancel(int customerId, int restaurantId) {

		try {
			String cancelUrl = urlStr + "/cancel";
			JsonDTO dto = new JsonDTO(customerId, restaurantId);
			String str = Request.postRequest(cancelUrl, dto);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 주의 !! DAO와 파라미터가 다름
	public int checkReservation(int customerId, int restaurantId) {
		int count = 0;
		try {
			String checkUrl = urlStr + "/check/" + "customerId=" + String.valueOf(customerId) + "&" + "restaurantId="
					+ String.valueOf(restaurantId);
			String str = Request.getRequest(checkUrl);
			count = Integer.parseInt(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ReservationDTO getReservationByCustomer(int customerId) {
		ReservationDTO dto = null;
		try {
			String selectUrl = urlStr + "/select/" + "customerId=" + String.valueOf(customerId) + "&" + "restaurantId="
					+ String.valueOf(0);
			String str = Request.getRequest(selectUrl);
			dto = gson.fromJson(str, ReservationDTO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public List<ReservationDTO> getReservationByRestaurantId(int restaurantId) {
		List<ReservationDTO> list = new ArrayList<>();
		try {
			String selectUrl = urlStr + "/restaurant/" + "restaurantId=" + String.valueOf(restaurantId) + "&";
			String str = Request.getRequest(selectUrl);
			Type dtoType = new TypeToken<List<ReservationDTO>>() {
			}.getType();
			list = gson.fromJson(str, dtoType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<ReservationForRestaurantDTO> getCustomerInfoByReservation(int restaurantId) {
		List<ReservationForRestaurantDTO> list = new ArrayList<>();
		try {
			String selectUrl = urlStr + "/customer/" + "restaurantId=" + String.valueOf(restaurantId) + "&";
			String str = Request.getRequest(selectUrl);
			Type dtoType = new TypeToken<List<ReservationForRestaurantDTO>>() {
			}.getType();
			list = gson.fromJson(str, dtoType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
