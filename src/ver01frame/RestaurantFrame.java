package ver01frame;

import ver01.RestaurantDTO;

public class RestaurantFrame {
	
	RestaurantDTO restaurantDTO;
	
	public RestaurantFrame(RestaurantDTO restaurantDTO) {
		this.restaurantDTO = restaurantDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		System.out.println(restaurantDTO);
	}

	private void setInitLayout() {
		
	}

	private void addEventListener() {
		
	}
}
