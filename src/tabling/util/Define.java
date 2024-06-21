package tabling.util;

public class Define {
	// 카테고리 인덱스
	public static final int CATEGORY_ALL=0;
	public static final int CATEGORY_GYOUNGYANG = 1;
	public static final int CATEGORY_CAFE = 2;
	public static final int CATEGORY_BOONSIK=3;
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
	public static final String SELECT_CATEGORY_BY_CATEGORYID=" SELECT * FROM category WHERE category_id = ? ";
}
