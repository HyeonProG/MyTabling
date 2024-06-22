package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDTO {
	
	private int foodId; // 음식 id
	private String foodName; // 음식 이름
	private int categoryId; // 카테고리 id

}
