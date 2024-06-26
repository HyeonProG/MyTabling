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

	// 예약 요청
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

	// 예약 취소 or 종료 요청
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
	// 해당 식당의 대기열 수 확인 요청 (해당 고객이 몇번째인지를 알려줌)
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

	// 해당 고객의 예약 정보 요청
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

	// 해당 식당의 모든 예약 리스트 확인
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

	// 해당 식당의 예약자 명단 확인
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
