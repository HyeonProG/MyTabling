package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 식당 리스트를 정렬 하기 위해 Comparable 인터페이스를 구현함
public class RestaurantDTO implements Comparable<RestaurantDTO> {

	public static final int RATING = 0;
	public static final int GANADA = 1;
	private static int sortType = RATING;

	private int restaurantId; // 가게 id
	private String restaurantName; // 가게 이름
	private String phone; // 가게 번호
	private String address; // 가게 주소
	private String content; // 가게 설명
	private String openTime; // 가게 오픈 시간
	private String closeTime; // 가게 폐점 시간
	private double rating; // 가게 평점
	private String restDay; // 가게 휴무일
	private int locationId; // 가게 위치
	private int categoryId; // 가게 카테고리
	
	public static void setSortType(int sortType) {
		RestaurantDTO.sortType = sortType;
	}
	
	@Override
	public int compareTo(RestaurantDTO dto) {
		if (sortType < 0) {
			sortType = 0;
		}
		switch (sortType) {
		case RATING:
			return (int) (dto.getRating() * 10 - rating * 10);
		case GANADA:
			StringBuilder str1 = new StringBuilder();
			StringBuilder str2 = new StringBuilder();
			for (char c : dto.getRestaurantName().toCharArray()) {
				str1.append(c);
			}
			for (char c : restaurantName.toCharArray()) {
				str2.append(c);
			}
			return str1.toString().compareTo(str2.toString());
		default:
			return 0;
		}
	}

}
