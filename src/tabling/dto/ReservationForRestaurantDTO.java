package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReservationForRestaurantDTO {

	private String customerName;
	private String customerPhone;
	private String reservationTime;
	private String state;
	
}
