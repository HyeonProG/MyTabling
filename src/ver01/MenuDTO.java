package ver01;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data  // getter setter 동시에 사용할수 있다.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MenuDTO {
	
	private int restaurant_id;
	private int food_id;
	private int price;

}
