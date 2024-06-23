package tabling.util;

public class Define {
	// 카테고리 인덱스
	public static final int CATEGORY_ALL = 0;
	public static final int CATEGORY_GYOUNGYANG = 1;
	public static final int CATEGORY_CAFE = 2;
	public static final int CATEGORY_BOONSIK = 3;
	public static final int CATEGORY_GUI = 4;
	public static final int CATEGORY_JOONGSIK = 5;
	public static final int CATEGORY_FASTFOOD = 6;
	public static final int CATEGORY_HANSIK = 7;
	public static final int CATEGORY_CHICKEN = 8;
	public static final int CATEGORY_HOE = 9;
	public static final int CATEGORY_FAMILIY = 10;
	public static final int CATEGORY_ILSIK = 11;
	public static final int CATEGORY_NAENGMYUN = 12;
	public static final int CATEGORY_HOF = 13;

	// 로케이션 인덱스
	public static final int LOCATION_ALL = 0;
	public static final int LOCATION_GANGSEOGU = 1;
	public static final int LOCATION_SAHAGU = 2;
	public static final int LOCATION_SASANGGU = 3;
	public static final int LOCATION_BUKGU = 4;
	public static final int LOCATION_SEOGU = 5;
	public static final int LOCATION_JUNGGU = 6;
	public static final int LOCATION_DONGGU = 7;
	public static final int LOCATION_BUSANSGINGU = 8;
	public static final int LOCATION_YEONGDOGU = 9;
	public static final int LOCATION_NAMGU = 10;
	public static final int LOCATION_DONGNAEGU = 11;
	public static final int LOCATION_YEONJEGU = 12;
	public static final int LOCATION_SUYEONGGU = 13;
	public static final int LOCATION_GEUMJEONGGU = 14;
	public static final int LOCATION_HAEUNDAEGU = 15;
	public static final int LOCATION_GIJANGGUN = 16;

	// 쿼리문
	public static final String SELECT_CATEGORY_BY_CATEGORYID = " SELECT * FROM category WHERE category_id = ? ";
	public static final String SELECT_CUSTOMER_BY_PHONE = " SELECT * FROM customer WHERE phone = ? ";
	public static final String SELECT_LIKES_BY_CUSTOMER_AND_RESTAURANT = " SELECT * FROM likes WHERE customer_id = ? AND restaurant_id = ? ";
	public static final String SELECT_LIKES_COUNT_BY_RESTAURANT = " SELECT count(*) FROM likes WHERE restaurant_id = ? ";
	public static final String SELECT_LOCATION_BY_LOCATIONNAME = " SELECT * FROM location WHERE location_id = ? ";
	public static final String SELECT_FOOD_BY_FOODID = " SELECT * FROM food WHERE food_id = ? ";
	public static final String SELECT_MENU_BY_RESTAURANTID = " SELECT * FROM menu WHERE restaurant_id = ? ";
	public static final String SELECT_RESERVATION_COUNT_BY_RESTID_AND_RESERID = " SELECT count(*) from reservation WHERE reservation_state = 'Y' AND restaurant_id = ? AND reservation_id <= ? ";
	public static final String SELECT_RESTAURANT_ALL = " SELECT * FROM restaurant ORDER BY CASE WHEN location_id = (SELECT location_id FROM customer WHERE customer_id = ? ) THEN 0 ELSE 1 END ";
	public static final String SELECT_RESTAURANT_ALL_FILTERED = " SELECT * FROM restaurant WHERE restaurant_id IN (SELECT restaurant_id FROM likes WHERE customer_id = ?) ORDER BY CASE WHEN location_id = (SELECT location_id FROM customer WHERE customer_id = ? ) THEN 0 ELSE 1 END ";
	public static final String SELECT_RESTAURANT_BY_CATEGROY = " SELECT * FROM restaurant where category_id = ? ";
	public static final String SELECT_RESTAURANT_BY_CATEGROY_FILTERED = " SELECT * FROM restaurant where category_id = ? AND restaurant_id IN (SELECT restaurant_id FROM likes WHERE customer_id = ?); ";
	public static final String SELECT_RESTAURANT_BY_LOCATION = " SELECT * FROM restaurant where location_id = ? ";
	public static final String SELECT_RESTAURANT_BY_LOCATION_FILTERED = " SELECT * FROM restaurant where location_id = ? AND restaurant_id IN (SELECT restaurant_id FROM likes WHERE customer_id = ?); ";
	public static final String SELECT_RESERVATION_BY_RESTAURANTID = " SELECT * FROM reservation WHERE restaurant_id = ? AND reservation_state = 'Y' ";
	public static final String SELECT_CUSTOMER_AND_RESERVATION = " SELECT customer_name, c.phone, reservation_time, state FROM restaurant as rest JOIN reservation as rev on rest.restaurant_id = rev.restaurant_id JOIN customer as c on rev.customer_id = c.customer_id WHERE rest.restaurant_id = ? ";

	public static final String INSERT_CUSTOMER = " INSERT INTO customer(customer_name, phone, location_id) VALUES(?, ?, ?) ";
	public static final String INSERT_RESERVATION = " INSERT INTO reservation(reservation_state, reservation_time, customer_id, restaurant_id) VALUES ('Y', current_timestamp(), ?, ?) ";
	public static final String INSERT_LIKES = " INSERT INTO likes VALUES(?, ?) ";

	public static final String UPDATE_CUSTOMER_INFO = " UPDATE customer SET customer_name = ?, location_id = ? WHERE phone = ? ";
	public static final String UPDATE_CUSTOMER_STATE_Y = " UPDATE customer SET state='Y' WHERE customer_id = ? ";
	public static final String UPDATE_CUSTOMER_STATE_N = " UPDATE customer SET state='N' WHERE customer_id = ? ";
	public static final String UPDATE_RESERVATION_STATE_N = " UPDATE reservation SET reservation_state='N' WHERE customer_id = ? AND restaurant_id = ? ";

	public static final String DELETE_LIKES = " DELETE FROM likes WHERE customer_id = ? AND restaurant_id = ? ";
}
