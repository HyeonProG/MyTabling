package tabling.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

import tabling.dao.CategoryDAO;
import tabling.dao.LocationDAO;
import tabling.dao.RestaurantDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.util.Time;

public class RestaurantListFrame extends JFrame {

	private BackgroundPanel bg;
	private RestaurantListFrame frame;
	private RestaurantFrame restaurantFrame;
	
	private JTable table;
	private JScrollPane scroll;
	private JComboBox<String> filter;
	private JButton filterBtn;
	private JLabel homeBtn;
	private JLabel backBtn;
	private JTextField searchField;
	private LocationDAO locationDAO;
	private CategoryDAO categoryDAO;
	private RestaurantDAO restaurantDAO;
	private CustomerDTO customerDTO;
	private int categoryId;
	private int locationId;
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

	private Time currentTime;
	private List<RestaurantDTO> restaurantList = new ArrayList<>();

	private String[] head = { "식당명", "카테고리", "지역", "영업중", "평점" };
	private String[][] contents;
	private TableRowSorter<DefaultTableModel> sorter;

	public RestaurantListFrame(List<RestaurantDTO> restaurantList, CustomerDTO customerDTO, int type) {
		this.customerDTO = customerDTO;
		this.restaurantList = restaurantList;
		this.type = type;
		typeSet();
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		bg = new BackgroundPanel();
		frame = this;
		locationDAO = new LocationDAO();
		categoryDAO = new CategoryDAO();
		restaurantDAO = new RestaurantDAO();
		// 임시 시간 설정
		currentTime = new Time(19, 30, "월요일");
		// 테이블에 담는 과정
		tableSet();
		filter = new JComboBox<String>();
		filterBtn = new JButton("적용");
		homeBtn = new JLabel(new ImageIcon("img/home.png"));
		backBtn = new JLabel(new ImageIcon("img/quitBtn2.png"));
		searchField = new JTextField();
	}

	private void setInitLayout() {
		bg.setSize(getWidth(),getHeight());
		bg.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(" 리스트 화면 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		// setLayout(null); // 좌표값으로 배치
		setResizable(false); // 프레임 조절 불가
		setLocationRelativeTo(null); // JFrame을 모니터 가운데 자동 배치

		bg.add(filter);
		filter.setLocation(270, 80);
		filter.setSize(110, 30);
		filter.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		for (int i = 0; i < COMBOBOX.length; i++) {
			filter.addItem(COMBOBOX[i]);
		}

		bg.add(filterBtn);
		filterBtn.setLocation(400, 80);
		filterBtn.setSize(70, 30);
		filterBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		bg.add(homeBtn);
		homeBtn.setLocation(200, 590);
		homeBtn.setSize(70, 70);

		bg.add(backBtn);
		backBtn.setLocation(20, 20);
		backBtn.setSize(15, 24);

		bg.add(searchField);
		searchField.setLocation(20, 80);
		searchField.setSize(202, 30);
		searchField.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		
		add(bg);
		setVisible(true);
		
	}

	private void addEventListener() {

		// 적용 버튼
		filterBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				switch (filter.getSelectedItem().toString()) {
				case RESET:
					// 걸려 있는 필터를 모두 초기화
					restaurantDAO.setOpenFilter(false);
					restaurantDAO.setLikeFilter(false);
					setRestaurantList();
					tableSet();
					break;
				case OPEN:
					restaurantDAO.setOpenFilter(true);
					// 현재 시간을 넣어 줄 수 있는데 편의상 설정된 시간을 넣음
					// currentTime = new Time(LocalDateTime.now().getHour(),
					// LocalDateTime.now().getMinute(),
					// LocalDateTime.now().getDayOfWeek().toString());
					restaurantDAO.setCurrentTime(currentTime);
					setRestaurantList();
					tableSet();
					break;
				case LIKE:
					restaurantDAO.setLikeFilter(true);
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

	// 테이블을 세팅하고, 이벤트도 등록하는 메서드
	private void tableSet() {
		if (scroll != null) {
			bg.remove(scroll);
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
			try {
				categoryName = categoryDAO.getCategoryName(categoryId);
				locationName = locationDAO.getLocationName(locationId);
			} catch (SQLException e) {
				e.printStackTrace();
			}

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
		bg.add(scroll);
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
		try {
			if (type == LOCATION) {
				restaurantList = restaurantDAO.getRestaurantsByLocation(locationId, customerDTO.getCustomerId());
			} else if (type == CATEGORY) {
				restaurantList = restaurantDAO.getRestaurantsByCategory(categoryId, customerDTO.getCustomerId());
			} else {
				restaurantList = restaurantDAO.getAllRestaurants(customerDTO.getCustomerId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/retaurantListFrameBg.jpg").getImage();
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
