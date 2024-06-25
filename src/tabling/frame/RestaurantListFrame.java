package tabling.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.RestaurantRequest;
import tabling.util.Define;
import tabling.util.Time;

public class RestaurantListFrame extends JFrame {

	// 패널
	private BackgroundPanel backgroundPanel;

	// 추후 다른 프레임에서 이 프레임을 끄기 위해 주소값 저장
	private RestaurantListFrame frame;
	// 한번에 식당 프레임은 하나만 띄워놓기 위해 변수 선언
	private RestaurantFrame restaurantFrame;

	// 컴포넌트
	private JComboBox<String> filter;
	private JLabel filterBtn;
	private JLabel homeBtn;
	private JLabel backBtn;
	private JTextField searchField;

	// 테이블 관련
	private JTable table;
	private JScrollPane scroll;
	private String[] head = { "식당명", "카테고리", "지역", "영업중", "평점" };
	private String[][] contents;
	private TableRowSorter<DefaultTableModel> sorter;

	// DTO
	private CustomerDTO customerDTO;

	// request
	private RestaurantRequest restaurantRequest;

	// 자주 사용하는 변수
	private int categoryId;
	private int locationId;

	// 뒤로가기 버튼 입력시 돌아가기 위해 어디로 부터 왔는지를 저장하는 변수
	private int type;
	public static final int CATEGORY = 0;
	public static final int LOCATION = 1;
	public static final int CATEGORY_ALL = 2;
	public static final int LOCATION_ALL = 3;

	// 필터 콤보 박스용 문자열
	private final String RESET = "초기화";
	private final String OPEN = "영업중";
	private final String LIKE = "좋아요만";
	private final String GANADA_UP = "가나다순";
	private final String GANADA_DOWN = "가나다역순";
	private final String RATING_UP = "평점역순";
	private final String RATING_DOWN = "평점순";
	private final String[] COMBOBOX = { RESET, OPEN, LIKE, GANADA_UP, GANADA_DOWN, RATING_UP, RATING_DOWN };

	// 영업중 필터 적용을 위해 시간을 다루는 클래스 선언
	private Time currentTime;

	// DTO
	private List<RestaurantDTO> restaurantList = new ArrayList<>();

	public RestaurantListFrame(List<RestaurantDTO> restaurantList, CustomerDTO customerDTO, int type) {
		this.customerDTO = customerDTO;
		this.restaurantList = restaurantList;
		this.type = type;
		typeSet();
		initData();
		setInitLayout();
		addEventListener();
	}

	/**
	 * 카테고리를 눌러서 들어왔을 경우 해당 카테고리를 저장 로케이션을 눌러서 들어왔을 경우 해당 로케이션을 저장
	 */
	private void typeSet() {
		if (!restaurantList.isEmpty()) {
			switch (type) {
			case CATEGORY:
				categoryId = restaurantList.get(0).getCategoryId();
				break;
			case LOCATION:
				locationId = restaurantList.get(0).getLocationId();
				break;
			}
		}
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel("img/retaurantListFrameBg.jpg");
		frame = this;
		restaurantRequest = new RestaurantRequest();
		// 현재 시간 설정
		currentTime = new Time(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
				LocalDateTime.now().getDayOfWeek().toString());
		// 테이블에 담는 과정
		tableSet();
		filter = new JComboBox<String>();
		filterBtn = new JLabel(new ImageIcon("img/적용버튼.png"));
		homeBtn = new JLabel(new ImageIcon("img/house-solid.png"));
		backBtn = new JLabel(new ImageIcon("img/quitBtn2.png"));
		searchField = new JTextField();
	}

	private void setInitLayout() {
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(" 리스트 화면 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		setResizable(false);
		setLocationRelativeTo(null);

		backgroundPanel.add(filter);
		filter.setBounds(260, 80, 110, 30);
		filter.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		for (int i = 0; i < COMBOBOX.length; i++) {
			filter.addItem(COMBOBOX[i]);
		}

		backgroundPanel.add(filterBtn);
		filterBtn.setBounds(385, 78, 86, 35);
		filterBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		backgroundPanel.add(homeBtn);
		homeBtn.setLocation(205, 595);
		homeBtn.setSize(70, 70);

		backgroundPanel.add(backBtn);
		backBtn.setLocation(20, 30);
		backBtn.setSize(15, 24);

		backgroundPanel.add(searchField);
		searchField.setLocation(20, 80);
		searchField.setSize(202, 30);
		searchField.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		add(backgroundPanel);
		setVisible(true);

	}

	private void addEventListener() {

		// 적용 버튼
		filterBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				searchField.setText("");
				switch (filter.getSelectedItem().toString()) {
				case RESET:
					// 걸려 있는 필터를 모두 초기화
					restaurantRequest.setOpenFilter(false);
					restaurantRequest.setLikeFilter(false);
					setRestaurantList();
					tableSet();
					break;
				case OPEN:
					restaurantRequest.setOpenFilter(true);
					setRestaurantList();
					tableSet();
					break;
				case LIKE:
					restaurantRequest.setLikeFilter(true);
					setRestaurantList();
					tableSet();
					break;
				// 테이블의 기능을 이용하면 되긴한데 일단 구현해봄
				case GANADA_UP:
					RestaurantDTO.setSortType(RestaurantDTO.GANADA);
					Collections.sort(restaurantList, Collections.reverseOrder());
					tableSet();
					break;
				case GANADA_DOWN:
					RestaurantDTO.setSortType(RestaurantDTO.GANADA);
					Collections.sort(restaurantList);
					tableSet();
					break;
				case RATING_DOWN:
					RestaurantDTO.setSortType(RestaurantDTO.RATING);
					Collections.sort(restaurantList);
					tableSet();
					break;
				case RATING_UP:
					RestaurantDTO.setSortType(RestaurantDTO.RATING);
					Collections.sort(restaurantList, Collections.reverseOrder());
					tableSet();
					break;
				}
			}
		});

		// 뒤로 가기 버튼 (어느 프레임으로 부터 왔는지를 기억하고 그 곳으로 돌아감)
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				switch (type) {
				case CATEGORY:
					new CategoryFrame(customerDTO);
					frame.setVisible(false);
					frame.dispose();
					break;
				case LOCATION:
					new LocationFrame(customerDTO);
					frame.setVisible(false);
					frame.dispose();
					break;
				case CATEGORY_ALL:
					new CategoryFrame(customerDTO);
					frame.setVisible(false);
					frame.dispose();
					break;
				case LOCATION_ALL:
					new LocationFrame(customerDTO);
					frame.setVisible(false);
					frame.dispose();
					break;
				}
			}
		});

		// 메인 메뉴로 돌아감
		homeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerMainMenuFrame(customerDTO);
				frame.setVisible(false);
				frame.dispose();
			}
		});

		// 테이블에 검색을 적용 시키기 위한 이벤트
		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
			}

			private void search() {
				String searchText = searchField.getText().trim();

				// 검색어가 없으면 모든 행을 다시 보여준다.
				if (searchText.isEmpty()) {
					sorter.setRowFilter(null);

				} else {
					try {
						sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
					} catch (PatternSyntaxException ex) {
						// 검색어가 정규식으로 변환되지 않으면 모든 행을 보여준다.
						sorter.setRowFilter(null);
					}
				}
			}
		});
	}

	// 테이블을 세팅하고, 이벤트도 등록하는 메서드
	private void tableSet() {
		if (scroll != null) {
			backgroundPanel.remove(scroll);
		}
		contents = new String[restaurantList.size()][head.length];

		// 테이블 내용 입력
		for (int i = 0; i < restaurantList.size(); i++) {
			String isOpen = null;
			if (currentTime.isOpen(restaurantList.get(i))) {
				isOpen = "O";
			} else {
				isOpen = "X";
			}
			String restaurantName = restaurantList.get(i).getRestaurantName();
			int categoryId = restaurantList.get(i).getCategoryId();
			double rating = restaurantList.get(i).getRating();
			int locationId = restaurantList.get(i).getLocationId();
			String categoryName = null;
			String locationName = null;
			/**
			 * 원래는 db에 접근해서 id에 해당하는 이름을 받아 왔으나 <BR>
			 * 1회 반복마다 서버에 요청을하니 너무 오래걸려서 자체적으로 처리함
			 */
			// categoryName = categoryRequest.getCategoryName(categoryId);
			// locationName = locationRequest.getLocationName(locationId);
			categoryName = getCategoryName(categoryId);
			locationName = getLocationName(locationId);

			contents[i][0] = restaurantName;
			contents[i][1] = categoryName;
			contents[i][2] = locationName;
			contents[i][3] = isOpen;
			contents[i][4] = String.valueOf(rating);
		}
		// 테이블 설정
		DefaultTableModel tableModel = new DefaultTableModel(contents, head);
		table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		// 테이블을 스크롤에 넣고 스크롤 설정
		scroll = new JScrollPane(table);
		backgroundPanel.add(scroll);
		scroll.setSize(450, 450);
		scroll.setLocation(20, 130);
		scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(0, 0));
				btn.setMinimumSize(new Dimension(0, 0));
				btn.setMaximumSize(new Dimension(0, 0));
				return btn;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(0, 0));
				btn.setMinimumSize(new Dimension(0, 0));
				btn.setMaximumSize(new Dimension(0, 0));
				return btn;
			}

			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.ORANGE;
				this.trackColor = Color.white;
			}
		});

		// 테이블 스타일 설정
		DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(JLabel.CENTER);
		table.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 불가
		table.getColumn("식당명").setPreferredWidth(200);
		table.getColumn("카테고리").setPreferredWidth(90);
		table.getColumn("카테고리").setCellRenderer(centerAlign);
		table.getColumn("지역").setPreferredWidth(70);
		table.getColumn("지역").setCellRenderer(centerAlign);
		table.getColumn("영업중").setPreferredWidth(40);
		table.getColumn("영업중").setCellRenderer(centerAlign);
		table.getColumn("평점").setPreferredWidth(30);
		table.getColumn("평점").setCellRenderer(centerAlign);
		repaint();

		// 테이블 이벤트 설정
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 더블 클릭시
				if (e.getClickCount() == 2) {
					int rowNum = table.getSelectedRow();
					int currentRowNum = table.convertRowIndexToModel(rowNum);
					String name = tableModel.getValueAt(currentRowNum, 0).toString();
					RestaurantDTO dto = null;
					for (RestaurantDTO restaurantDTO : restaurantList) {
						if (restaurantDTO.getRestaurantName().equals(name)) {
							dto = restaurantDTO;
						}
					}
					if (dto == null) {
						return;
					}
					if (restaurantFrame != null) {
						restaurantFrame.setVisible(true);
						restaurantFrame.dispose();
					}
					restaurantFrame = new RestaurantFrame(customerDTO, dto, frame);
				}
			}
		});
	}

	// 특정 필터가 결렸을때 다시 식당 리스트를 불러 오기 위한 메서드
	private void setRestaurantList() {
		if (type == LOCATION) {
			restaurantList = restaurantRequest.getRestaurantsByLocation(locationId, customerDTO.getCustomerId());
		} else if (type == CATEGORY) {
			restaurantList = restaurantRequest.getRestaurantsByCategory(categoryId, customerDTO.getCustomerId());
		} else {
			restaurantList = restaurantRequest.getAllRestaurants(customerDTO.getCustomerId());
		}
	}

	/**
	 * id를 이름으로 바꾸는 메서드 <BR>
	 * 원래는 db에서 받아왔으나 서버에 요청을 하는 방식으로는 너무 오래걸려 바꿈
	 * 
	 * @param id
	 * @return
	 */
	private String getCategoryName(int id) {
		for (int i = Define.CATEGORY_GYOUNGYANG; i <= Define.CATEGORY_HOF; i++) {
			if (i == id) {
				return Define.CATEGOIES[i];
			}
		}
		return null;
	}

	private String getLocationName(int id) {
		for (int i = Define.LOCATION_GANGSEOGU; i <= Define.LOCATION_GIJANGGUN; i++) {
			if (i == id) {
				return Define.LOCATIONS[i];
			}
		}
		return null;
	}
}
