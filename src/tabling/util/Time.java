package tabling.util;

import lombok.Data;
import tabling.dto.RestaurantDTO;

@Data
// 영업중임을 확인 하기 위해 만든 클래스
public class Time {
	private int currentHour;
	private int currentMinute;
	private String currentDay;
	private boolean timeCheck;
	private boolean dayCheck;

	public Time(int currentHour, int currentMinute, String currentDay) {
		if (currentHour < 6) {
			currentHour += 24;
		}
		this.currentHour = currentHour;
		this.currentMinute = currentMinute;
		switch (currentDay) {
		case "MONDAY":
			currentDay = "월요일";
			break;
		case "TUESDAY":
			currentDay = "화요일";
			break;
		case "WEDNESDAY":
			currentDay = "수요일";
			break;
		case "THURSDAY":
			currentDay = "목요일";
			break;
		case "FRIDAY":
			currentDay = "금요일";
			break;
		case "SATURDAY":
			currentDay = "토요일";
			break;
		case "SUNDAY":
			currentDay = "일요일";
			break;
		}
		this.currentDay = currentDay;
	}

	// 식당의 영업 시간을 메서드를 호출하는 객체에 입력된 시간과 비교하는 메서드
	public boolean isOpen(RestaurantDTO dto) {
		boolean result;
		String openStr = dto.getOpenTime();
		String closeStr = dto.getCloseTime();
		String restDay = dto.getRestDay();
		String[] openTime = openStr.split(":");
		String[] closeTime = closeStr.split(":");
		int openHour = Integer.parseInt(openTime[0]);
		int openMinute = Integer.parseInt(openTime[1]);
		int closeHour = Integer.parseInt(closeTime[0]);
		int closeMinute = Integer.parseInt(closeTime[1]);
		if (closeHour < 6) {
			closeHour += 24;
		}
		if (openHour < currentHour && currentHour < closeHour) {
			timeCheck = true;
		}
		if (openHour == currentHour && openMinute <= currentMinute) {
			timeCheck = true;
		}
		if (closeHour == currentHour && currentMinute <= closeMinute) {
			timeCheck = true;
		}
		if (!currentDay.equals(restDay)) {
			dayCheck = true;
		}
		result = timeCheck && dayCheck;
		timeCheck = false;
		dayCheck = false;
		return result;
	}
}
