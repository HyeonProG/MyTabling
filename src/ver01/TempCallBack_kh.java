package ver01;

import java.util.ArrayList;
import java.util.List;

import ver01frame.ListFrame;

public class TempCallBack_kh {
	public void clickCategory(int categoryId) {
		List<RestaurantDTO> list = new ArrayList<>();
		new ListFrame(list);
	}
}
