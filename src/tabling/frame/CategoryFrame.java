package tabling.frame;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tabling.dao.RestaurantDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.util.Define;

public class CategoryFrame extends JFrame implements MouseListener {

	private JLabel[] categoryImgs;
	private JLabel[] categoryTexts;

	private JLabel homeLable;

	private BackgroundPanel backgroundPanel;
	private MainPanel mainPanel;
	private JScrollPane scrollPane;
	private CustomerLoginFrame customerLoginFrame;
	private CustomerDTO customerDTO;

	public CategoryFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		// 그림라벨
		categoryImgs = new JLabel[14];
		categoryImgs[Define.CATEGORY_ALL] = new JLabel(new ImageIcon("img/전체보기.png"));
		categoryImgs[Define.CATEGORY_GYOUNGYANG] = new JLabel(new ImageIcon("img/경양식.png"));
		categoryImgs[Define.CATEGORY_CAFE] = new JLabel(new ImageIcon("img/카페.png"));
		categoryImgs[Define.CATEGORY_BOONSIK] = new JLabel(new ImageIcon("img/분식.png"));
		categoryImgs[Define.CATEGORY_GUI] = new JLabel(new ImageIcon("img/구이.png"));
		categoryImgs[Define.CATEGORY_JOONGSIK] = new JLabel(new ImageIcon("img/중식.png"));
		categoryImgs[Define.CATEGORY_FASTFOOD] = new JLabel(new ImageIcon("img/패스트푸드.png"));
		categoryImgs[Define.CATEGORY_HANSIK] = new JLabel(new ImageIcon("img/한식.png"));
		categoryImgs[Define.CATEGORY_CHICKEN] = new JLabel(new ImageIcon("img/치킨.png"));
		categoryImgs[Define.CATEGORY_HOE] = new JLabel(new ImageIcon("img/회.png"));
		categoryImgs[Define.CATEGORY_FAMILIY] = new JLabel(new ImageIcon("img/패밀리레스토랑.png"));
		categoryImgs[Define.CATEGORY_ILSIK] = new JLabel(new ImageIcon("img/일식.png"));
		categoryImgs[Define.CATEGORY_NAENGMYUN] = new JLabel(new ImageIcon("img/냉면.png"));
		categoryImgs[Define.CATEGORY_HOF] = new JLabel(new ImageIcon("img/호프.png"));
		mainPanel = new MainPanel();
		scrollPane = new JScrollPane(mainPanel);

		homeLable = new JLabel(new ImageIcon("img/home.png"));

		for (int i = 0; i < 14; i++) {
			mainPanel.add(categoryImgs[i]);
		}

	}

	private void setInitLayout() {
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);

		homeLable.setBounds(40, 5, 70, 70);
		add(homeLable);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		scrollPane.setBounds(40, 120, 400, 420);
		backgroundPanel.add(scrollPane);

		mainPanel.setLayout(new GridLayout(0, 1));
	}

	private void initListener() {
		for (int i = Define.CATEGORY_ALL; i < Define.CATEGORY_HOF; i++) {
			categoryImgs[i].addMouseListener(this);

		}
		homeLable.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {

			
	for(int i=0;i<3;i++) {
		 if (i==0) {			 
			 if(e.getSource() == categoryImgs[i]  ) {
				 
					RestaurantDAO dao = new RestaurantDAO();
					List<RestaurantDTO> list = dao.getAllRestaurants();
					new RestaurantListFrame(list, customerDTO, RestaurantListFrame.CATEGORY);	
			 }
		} else {
	 
		  if (e.getSource() == categoryImgs[i]  ) {
			RestaurantDAO dao = new RestaurantDAO();
				List<RestaurantDTO> list = dao.getRestaurantsByCategory(i);
				new RestaurantListFrame(list, customerDTO, RestaurantListFrame.CATEGORY);
		  			}
		 	}  
		 
		 
		 if(e.getSource() == homeLable) {
			 new CustomerMainMenuFrame(customerDTO);


			for (int i = Define.CATEGORY_ALL; i < Define.CATEGORY_HOF; i++) {
				if (e.getSource() == categoryImgs[i]) {
					if (i == Define.CATEGORY_ALL) {
						RestaurantDAO dao = new RestaurantDAO();
						List<RestaurantDTO> list = dao.getAllRestaurants();
						new RestaurantListFrame(list);
						break;

					} else {
						RestaurantDAO dao = new RestaurantDAO();
						List<RestaurantDTO> list = dao.getRestaurantsByCategory(i);
						new RestaurantListFrame(list);
						break;
					}
				}
			}
			
			if (e.getSource() == homeLable) {
				new CustomerMainMenuFrame(customerDTO);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
		}
	}

	private class BackgroundPanel extends JPanel {
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/backgroundimage.jpg").getImage();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

	private class MainPanel extends JPanel {
		private Image backgroundImage;

		public MainPanel() {
			backgroundImage = new ImageIcon("img/backgroundimage.jpg").getImage();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
