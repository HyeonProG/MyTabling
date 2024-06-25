package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 식당 측 예약 현황을 확인 하기위한 프레임에 띄울 컬럼을 모아 놓은 클래스
public class ReservationForRestaurantDTO {

	private String customerName; // 고객 이름
	private String customerPhone; // 고객 전화번호
	private String reservationTime; // 예약 시간
	private String state; // 예약 상태

}
