package ver01;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Restaurant {
	
	private int restaurant_id; // 가게 id
	private String restaurant_name; // 가게 이름
	private int phone; // 가게 번호
	private String address; // 가게 주소
	private String content; // 가게 설명
	private String open_time; // 가게 오픈 시간
	private String close_time; // 가게 폐점 시간
	private double rating; // 가게 평점
	private int likes; // 가게 종아요
	private String rest_day; // 가게 휴무일
	private int location_id; // 가게 위치
	private int category_id; // 가게 카테고리
	

}
