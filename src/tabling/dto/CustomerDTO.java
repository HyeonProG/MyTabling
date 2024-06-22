package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

	private int customerId; // 고객 id
	private String customerName; // 고객 이름
	private String phone; // 고객 전화번호
	private String state; // 예약 상태
	private int locationId; // 선호 지역 id
	
}
