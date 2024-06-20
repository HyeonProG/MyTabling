package ver01frame;

import javax.swing.JButton;

import ver01.MenuDTO;
import ver01.RestaurantDTO;

public class RestaurantFrame {
	
	RestaurantDTO restaurantDTO;
	MenuDTO menuDTO;
	
	private JButton backBtn;
	
	
	public RestaurantFrame(RestaurantDTO restaurantDTO, MenuDTO menuDTO) {
		this.restaurantDTO = restaurantDTO;
		this.menuDTO = menuDTO;
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
