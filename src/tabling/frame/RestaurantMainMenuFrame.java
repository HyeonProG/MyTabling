package tabling.frame;

import javax.swing.JFrame;

import tabling.dto.RestaurantDTO;

public class RestaurantMainMenuFrame extends JFrame {

	private RestaurantDTO restaurantDTO;

	public RestaurantMainMenuFrame(RestaurantDTO restaurantDTO) {
		this.restaurantDTO = restaurantDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {

		// TODO Auto-generated method stub

	}

	private void setInitLayout() {
		setTitle("Tabling - " + restaurantDTO.getRestaurantName());
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);

	}

	private void initListener() {
		// TODO Auto-generated method stub

	}

}
