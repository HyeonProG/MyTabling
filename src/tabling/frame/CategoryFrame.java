package tabling.frame;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tabling.dao.RestaurantDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.util.Define;
import tabling.util.MyMouseListener;

public class CategoryFrame extends JFrame implements MyMouseListener {

	private JLabel[] categoryImgs;

	private JLabel homeLabel;
	private JLabel adLabel;

	private BackgroundPanel backgroundPanel;
	private JLabel mainLabel;
	private CustomerDTO customerDTO;
	private RestaurantDAO dao;

	public CategoryFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		dao = new RestaurantDAO();
		backgroundPanel = new BackgroundPanel();
		// 그림라벨
		categoryImgs = new JLabel[14];
		categoryImgs[Define.CATEGORY_ALL] = new JLabel(new ImageIcon("img/전체보기버튼.png"));
		categoryImgs[Define.CATEGORY_GYOUNGYANG] = new JLabel(new ImageIcon("img/경양식버튼.png"));
		categoryImgs[Define.CATEGORY_CAFE] = new JLabel(new ImageIcon("img/카페버튼.png"));
		categoryImgs[Define.CATEGORY_BOONSIK] = new JLabel(new ImageIcon("img/분식버튼.png"));
		categoryImgs[Define.CATEGORY_GUI] = new JLabel(new ImageIcon("img/구이버튼.png"));
		categoryImgs[Define.CATEGORY_JOONGSIK] = new JLabel(new ImageIcon("img/중식버튼.png"));
		categoryImgs[Define.CATEGORY_FASTFOOD] = new JLabel(new ImageIcon("img/패스트푸드버튼.png"));
		categoryImgs[Define.CATEGORY_HANSIK] = new JLabel(new ImageIcon("img/한식버튼.png"));
		categoryImgs[Define.CATEGORY_CHICKEN] = new JLabel(new ImageIcon("img/치킨버튼.png"));
		categoryImgs[Define.CATEGORY_HOE] = new JLabel(new ImageIcon("img/회버튼.png"));
		categoryImgs[Define.CATEGORY_FAMILY] = new JLabel(new ImageIcon("img/패밀리레스토랑버튼.png"));
		categoryImgs[Define.CATEGORY_ILSIK] = new JLabel(new ImageIcon("img/일식버튼.png"));
		categoryImgs[Define.CATEGORY_NAENGMYUN] = new JLabel(new ImageIcon("img/냉면버튼.png"));
		categoryImgs[Define.CATEGORY_HOF] = new JLabel(new ImageIcon("img/호프버튼.png"));
		mainLabel = new JLabel();

		for (int i = 0; i < 14; i++) {
			mainLabel.add(categoryImgs[i]);
		}

		homeLabel = new JLabel(new ImageIcon("img/house-solid.png"));
		adLabel = new JLabel(new ImageIcon());

	}

	private void setInitLayout() {
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

	private void initListener() {
		for (int i = Define.CATEGORY_ALL; i <= Define.CATEGORY_HOF; i++) {
			categoryImgs[i].addMouseListener(this);

		}
		homeLabel.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			for (int i = Define.CATEGORY_ALL; i <= Define.CATEGORY_HOF; i++) {
				if (e.getSource() == categoryImgs[Define.CATEGORY_ALL]) {
					List<RestaurantDTO> list = dao.getAllRestaurants(customerDTO.getCustomerId());
					new RestaurantListFrame(list, customerDTO, RestaurantListFrame.CATEGORY_ALL);
					setVisible(false);
					dispose();
					break;

				}
				if (e.getSource() == categoryImgs[i]) {
					List<RestaurantDTO> list = dao.getRestaurantsByCategory(i, customerDTO.getCustomerId());
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

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

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
