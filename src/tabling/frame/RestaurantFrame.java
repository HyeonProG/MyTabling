package tabling.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

import tabling.dao.LikeDAO;
import tabling.dao.MenuDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.MenuDTO;
import tabling.dto.RestaurantDTO;

public class RestaurantFrame extends JFrame {
	private RestaurantFrame frame;
	private RestaurantListFrame restaurantListFrame;
	
	private RestaurantDTO restaurantDTO;
	private List<MenuDTO> menuList = new ArrayList<>();
	private MenuDAO menuDAO;
	private CustomerDTO customerDTO;
	
	private JLabel imageLabel;
	private JLabel ratingLabel;
	private JLabel likeLabel;
	private ImageIcon likeImg;
	private ImageIcon unlikeImg;
	private boolean checkLike;
	
	private JTable table;
	private JScrollPane tableScroll;
	private Vector<String> head = new Vector<>();
	private Vector<Vector<String>> contents = new Vector<>();

	private JButton reservationBtn;

	private JLabel restaurantNameLabel;
	private JTextArea restaurantDetail;
	private JScrollPane detailScroll;
	
	private LikeDAO likeDAO;
	private JLabel likeCountLabel;

	public RestaurantFrame(CustomerDTO customerDTO, RestaurantDTO restaurantDTO, RestaurantListFrame restaurantListFrame) {
		this.customerDTO = customerDTO;
		this.restaurantDTO = restaurantDTO;
		this.restaurantListFrame = restaurantListFrame;
		menuDAO = new MenuDAO();
		try {
			this.menuList = menuDAO.getMenuByRestaurantId(restaurantDTO.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		frame = this;
		
		setTitle("음식점 상세페이지");
		setSize(500, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		
		likeDAO = new LikeDAO();
		// 현재 고객이 좋아요를 누른 식당인지 판별 -> 프레임에 띄우기 위함
		try {
			checkLike = likeDAO.getLike(customerDTO.getCustomerId(), restaurantDTO.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		imageLabel = new JLabel(new ImageIcon("img/waitingimage.jpg"));

		restaurantNameLabel = new JLabel();
		restaurantNameLabel.setText(restaurantDTO.getRestaurantName());
		restaurantDetail = new JTextArea();
		restaurantDetail.setLineWrap(true);
		restaurantDetail.append("주소 : " + restaurantDTO.getAddress() + "\n");
		restaurantDetail.append("가게 번호 : " + restaurantDTO.getPhone() + "\n");
		restaurantDetail.append("가게 오픈 시간 : " + restaurantDTO.getOpenTime() + " ~ " + restaurantDTO.getCloseTime() + "\n");
		restaurantDetail.append("가게 휴무일 : " + restaurantDTO.getRestDay() + "\n");
		restaurantDetail.append("가게 위치 : " + restaurantDTO.getAddress() + "\n");
		restaurantDetail.append("상세 설명 \n");
		restaurantDetail.append(restaurantDTO.getContent() + "\n");
		restaurantDetail.setEditable(false);

		head.add("메뉴 이름");
		head.add("가격");
		for (int i = 0; i < menuList.size(); i++) {
			contents.add(new Vector<>());
			int price = menuList.get(i).getPrice();
			int foodId = menuList.get(i).getFoodId();
			String menuName = null;
			try {
				menuName = menuDAO.getFoodName(foodId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			contents.get(i).add(menuName);
			contents.get(i).add(String.valueOf(price));
		}

		table = new JTable(contents, head) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		reservationBtn = new JButton("예약하기");

		tableScroll = new JScrollPane(table);

		detailScroll = new JScrollPane(restaurantDetail);
		
		ratingLabel = new JLabel();
		ratingLabel.setText("평점 : " + restaurantDTO.getRating());
		ratingLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		
		likeImg = new ImageIcon("img/like.png");
		unlikeImg = new ImageIcon("img/unlike.png");
		
		// 좋아요를 누른 식당이라면 꽉찬 하트 적용
		if (checkLike == false) {
			likeLabel = new JLabel(unlikeImg);			
		} else {
			likeLabel = new JLabel(likeImg);
		}
		
		likeCountLabel = new JLabel();
		// 좋아요 수 카운트
		try {
			likeCountLabel.setText(String.valueOf(likeDAO.getLikeCount(restaurantDTO.getRestaurantId())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setVisible(true);

	}

	private void setInitLayout() {
		likeLabel.setBounds(60, 45, 50, 50);
		add(likeLabel);
		likeLabel.setVisible(true);
		
		reservationBtn.setBounds(320, 50, 90, 30);
		add(reservationBtn);

		add(tableScroll);
		tableScroll.setSize(350, 148);
		tableScroll.setLocation(65, 400);

		table.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 불가
		table.getColumn("메뉴 이름").setPreferredWidth(200);
		table.getColumn("가격").setPreferredWidth(40);
		DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(JLabel.CENTER);
		table.getColumn("가격").setCellRenderer(centerAlign);
		table.getColumn("메뉴 이름").setPreferredWidth(70);
		table.getColumn("메뉴 이름").setCellRenderer(centerAlign);

		restaurantNameLabel.setBounds(65, 110, 150, 50);
		restaurantNameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		restaurantNameLabel.setOpaque(true);
		restaurantNameLabel.setBackground(Color.WHITE);
		add(restaurantNameLabel);

		add(detailScroll);
		detailScroll.setSize(350, 150);
		detailScroll.setLocation(65, 250);

		restaurantDetail.setBounds(65, 250, 350, 150);
		restaurantDetail.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		restaurantDetail.setOpaque(true);
		restaurantDetail.setBackground(Color.WHITE);
		

		imageLabel.setBounds(250, 100, 170, 150);
		add(imageLabel);
		
		ratingLabel.setBounds(65, 170, 150, 50);
		ratingLabel.setBackground(Color.WHITE);
		ratingLabel.setOpaque(true);
		add(ratingLabel);
		
		likeCountLabel.setBounds(120, 45, 50, 50);
		likeCountLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 30));
		add(likeCountLabel);

	}

	private void addEventListener() {
		
		// 예약하기 버튼 이벤트
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new ReservationFrame(customerDTO, restaurantDTO, restaurantListFrame);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		// 좋아요 버튼 이벤트
		// 이미 좋아요 누른 상태면 해제, 반대면 적용
		likeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (checkLike == false) {
					try {
						// 좋아요 테이블에 추가하고, 해당 식당의 변경된 좋아요 수를 받아옴
						int likeCount = likeDAO.addLike(customerDTO.getCustomerId(), restaurantDTO.getRestaurantId());
						likeCountLabel.setText(String.valueOf(likeCount));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					likeLabel.setIcon(likeImg);
					checkLike = true;
				} else {
					try {
						// 좋아요 테이블에서 삭제하고, 해당 식당의 변경된 좋아요 수를 받아옴
						int likeCount = likeDAO.deleteLike(customerDTO.getCustomerId(), restaurantDTO.getRestaurantId());
						likeCountLabel.setText(String.valueOf(likeCount));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					likeLabel.setIcon(unlikeImg);
					checkLike = false;
				}
			}
		});		

	}

}
