package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {

	private int restaurantId; // 식당 id
	private int foodId; // 음식 id
	private int price; // 음식 가격

}
