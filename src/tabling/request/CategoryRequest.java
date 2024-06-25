package tabling.request;

import java.io.IOException;

public class CategoryRequest {
	private String urlStr;

	public CategoryRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/category";
	}

	public String getCategoryName(int categoryId) {
		String str = null;
		try {
			String selectUrl = urlStr + "/" + categoryId;
			str = Request.getRequest(selectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

}
