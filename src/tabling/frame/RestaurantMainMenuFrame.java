package tabling.frame;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import tabling.dto.RestaurantDTO;

public class RestaurantMainMenuFrame extends JFrame {

	private RestaurantDTO restaurantDTO;
	// TODO - 테이블로 리스트 띄우기 하는 중
	private JTable table;
	private JScrollPane scroll;
	private JComboBox<String> filter;

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
