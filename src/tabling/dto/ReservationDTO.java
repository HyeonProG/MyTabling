package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
	
	private int reservationId; // 예약 id
	private String reservationState; // 예약 상태
	private String reservationTime; // 예약 시간
	private int customerId; // 고객 id
	private int restaurantId; // 식당 id
	
}
