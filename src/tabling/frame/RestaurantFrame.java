package tabling.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

import tabling.dto.CustomerDTO;
import tabling.dto.MenuDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.LikeRequest;
import tabling.request.MenuRequest;

public class RestaurantFrame extends JFrame {

	// 예약 프레임에 넘겨주기 위해 참조함
	private RestaurantListFrame restaurantListFrame;

	// 컴포넌트
	private JLabel background;
	private JLabel imageLabel;
	private JLabel ratingLabel;
	private JLabel restaurantNameLabel;
	private JTextArea restaurantDetail;
	private JScrollPane detailScroll;
	private JLabel likeLabel;
	private ImageIcon likeImg;
	private ImageIcon unlikeImg;
	private JLabel likeCountLabel;
	private JLabel reservationBtn;

	// 테이블 관련 변수
	private JTable table;
	private JScrollPane tableScroll;
	private Vector<String> head = new Vector<>();
	private Vector<Vector<String>> contents = new Vector<>();

	// DTO
	private RestaurantDTO restaurantDTO;
	private CustomerDTO customerDTO;
	private List<MenuDTO> menuList = new ArrayList<>();

	// request
	private MenuRequest menuRequest;
	private LikeRequest likeRequest;

	// 좋아요 버튼이 눌러졌는지 체크하는 변수
	private boolean checkLike;

	public RestaurantFrame(CustomerDTO customerDTO, RestaurantDTO restaurantDTO,
			RestaurantListFrame restaurantListFrame) {
		this.customerDTO = customerDTO;
		this.restaurantDTO = restaurantDTO;
		this.restaurantListFrame = restaurantListFrame;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {

		setTitle("음식점 상세페이지");
		setSize(500, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		background = new JLabel(new ImageIcon("img/retaurantFrameBg.jpg"));

		likeRequest = new LikeRequest();
		menuRequest = new MenuRequest();
		menuList = menuRequest.getMenuByRestaurantId(restaurantDTO.getRestaurantId());

		// 현재 고객이 좋아요를 누른 식당인지 판별 -> 프레임에 띄우기 위함
		checkLike = likeRequest.getLike(customerDTO.getCustomerId(), restaurantDTO.getRestaurantId());

		imageLabel = new JLabel(new ImageIcon("img/waitingimage.jpg"));

		restaurantNameLabel = new JLabel();
		restaurantNameLabel.setText(restaurantDTO.getRestaurantName());

		restaurantDetail = new JTextArea();
		restaurantDetail.setLineWrap(true);
		// 식당의 상세 정보를 작성
		restaurantDetail.append("주소 : " + restaurantDTO.getAddress() + "\n");
		restaurantDetail.append("가게 번호 : " + restaurantDTO.getPhone() + "\n");
		restaurantDetail
				.append("가게 오픈 시간 : " + restaurantDTO.getOpenTime() + " ~ " + restaurantDTO.getCloseTime() + "\n");
		restaurantDetail.append("가게 휴무일 : " + restaurantDTO.getRestDay() + "\n");
		restaurantDetail.append("가게 위치 : " + restaurantDTO.getAddress() + "\n");
		restaurantDetail.append("상세 설명 \n");
		restaurantDetail.append(restaurantDTO.getContent() + "\n");
		restaurantDetail.setEditable(false);

		// 테이블 컬럼 명 초기화
		head.add("메뉴 이름");
		head.add("가격");

		// 테이블 내용 초기화
		for (int i = 0; i < menuList.size(); i++) {
			contents.add(new Vector<>());
			int price = menuList.get(i).getPrice();
			int foodId = menuList.get(i).getFoodId();
			String menuName = null;
			menuName = menuRequest.getFoodName(foodId);
			contents.get(i).add(menuName);
			contents.get(i).add(String.valueOf(price));
		}

		// 테이블 초기화와 동시에 수정 불가 적용
		table = new JTable(contents, head) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		reservationBtn = new JLabel(new ImageIcon("img/예약하기버튼1.png"));

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
		likeCountLabel.setText(String.valueOf(likeRequest.getLikeCount(restaurantDTO.getRestaurantId())));
		setVisible(true);

	}

	private void setInitLayout() {
		background.setSize(getWidth(), getHeight());
		add(background);

		likeLabel.setBounds(60, 95, 50, 50);
		background.add(likeLabel);
		likeLabel.setVisible(true);

		reservationBtn.setBounds(340, 27, 90, 40);
		background.add(reservationBtn);

		background.add(tableScroll);
		tableScroll.setSize(350, 148);
		tableScroll.setLocation(65, 430);

		// 테이블 설정
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

		restaurantNameLabel.setBounds(65, 150, 150, 50);
		restaurantNameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 30));
		restaurantNameLabel.setOpaque(true);
		restaurantNameLabel.setBackground(Color.WHITE);
		background.add(restaurantNameLabel);

		background.add(detailScroll);
		detailScroll.setSize(350, 150);
		detailScroll.setLocation(65, 280);

		restaurantDetail.setBounds(65, 20, 350, 150);
		restaurantDetail.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		restaurantDetail.setOpaque(true);
		restaurantDetail.setBackground(Color.WHITE);

		imageLabel.setBounds(250, 100, 170, 150);
		background.add(imageLabel);

		ratingLabel.setBounds(65, 210, 150, 50);
		ratingLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 20));
		ratingLabel.setBackground(Color.WHITE);
		ratingLabel.setOpaque(true);
		background.add(ratingLabel);

		likeCountLabel.setBounds(120, 95, 50, 50);
		likeCountLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 30));
		background.add(likeCountLabel);

	}

	private void addEventListener() {

		// 예약하기 버튼 이벤트
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new ReservationFrame(customerDTO, restaurantDTO, restaurantListFrame);
				setVisible(false);
				dispose();
			}
		});

		// 좋아요 버튼 이벤트
		// 이미 좋아요 누른 상태면 해제, 반대면 적용
		likeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (checkLike == false) {
					// 좋아요 테이블에 추가하고, 해당 식당의 변경된 좋아요 수를 받아옴
					int likeCount = likeRequest.addLike(customerDTO.getCustomerId(), restaurantDTO.getRestaurantId());
					likeCountLabel.setText(String.valueOf(likeCount));
					likeLabel.setIcon(likeImg);
					checkLike = true;
				} else {
					// 좋아요 테이블에서 삭제하고, 해당 식당의 변경된 좋아요 수를 받아옴
					int likeCount = likeRequest.deleteLike(customerDTO.getCustomerId(),
							restaurantDTO.getRestaurantId());
					likeCountLabel.setText(String.valueOf(likeCount));
					likeLabel.setIcon(unlikeImg);
					checkLike = false;
				}
			}
		});

	}

}
