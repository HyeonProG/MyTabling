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

	private int reservation_id;
	private Boolean reservation_state;
	private String reservation_time;
	private int customer_id;
	private int restaurant_id;
}
