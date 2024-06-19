package ver01;

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
public class ReservationDTO {

	private int reservationId;
	private String reservationState;
	private String reservationTime;
	private int customerId;
	private int restaurantId;
}
