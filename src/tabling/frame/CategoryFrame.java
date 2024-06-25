package tabling.frame;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.RestaurantRequest;
import tabling.util.Define;
import tabling.util.MyMouseListener;

// 카테고리 선택 화면
public class CategoryFrame extends JFrame implements MyMouseListener {

	// 패널
	private BackgroundPanel backgroundPanel;

	// 컴포넌트
	private JLabel[] categoryImgs;
	private JLabel homeLabel;
	private JLabel mainLabel;
	
	// DTO
	private CustomerDTO customerDTO;
	
	// request
	private RestaurantRequest restaurantRequest;

	public CategoryFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		restaurantRequest = new RestaurantRequest();
		mainLabel = new JLabel();

		// 그림라벨 초기화
		categoryImgs = new JLabel[14];
		String categoryStr = "img/category_";
		for (int i = Define.CATEGORY_ALL; i <= Define.CATEGORY_HOF; i++) {
			categoryImgs[i] = new JLabel(new ImageIcon(categoryStr + i + ".png"));
			mainLabel.add(categoryImgs[i]);
		}

		homeLabel = new JLabel(new ImageIcon("img/house-solid.png"));

	}

	private void setInitLayout() {
		setTitle("카테고리");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		homeLabel.setBounds(217, 605, 50, 50);
		add(homeLabel);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		mainLabel.setBounds(48, 180, 400, 420);
		backgroundPanel.add(mainLabel);

		mainLabel.setLayout(new GridLayout(3, 4));

	}

	private void addEventListener() {
		for (int i = Define.CATEGORY_ALL; i <= Define.CATEGORY_HOF; i++) {
			categoryImgs[i].addMouseListener(this);

		}
		homeLabel.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 전체 보기 선택시
		if (e.getSource() == categoryImgs[Define.CATEGORY_ALL]) {
			List<RestaurantDTO> list = restaurantRequest.getAllRestaurants(customerDTO.getCustomerId());
			new RestaurantListFrame(list, customerDTO, RestaurantListFrame.CATEGORY_ALL);
			setVisible(false);
			dispose();
		}
		
		// 어떤 카테고리를 선택했는지 확인
		for (int i = Define.CATEGORY_GYOUNGYANG; i <= Define.CATEGORY_HOF; i++) {
			if (e.getSource() == categoryImgs[i]) {
				List<RestaurantDTO> list = restaurantRequest.getRestaurantsByCategory(i, customerDTO.getCustomerId());
				new RestaurantListFrame(list, customerDTO, RestaurantListFrame.CATEGORY);
				setVisible(false);
				dispose();
				break;
			}
		}

		if (e.getSource() == homeLabel) {
			new CustomerMainMenuFrame(customerDTO);
			setVisible(false);
			dispose();
		}

	}

	// 패널 내부 클래스
	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/categoryFrameBg.jpg").getImage();
			backgroundPanel = new JPanel();
			add(backgroundPanel);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

}
