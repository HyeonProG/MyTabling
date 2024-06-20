package ver01;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
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
		this.currentDay = currentDay;
	}

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
