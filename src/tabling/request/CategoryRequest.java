package tabling.request;

import java.io.IOException;

public class CategoryRequest {
	private String urlStr;

	public CategoryRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/category";
	}

	/**
	 * 매번 요청을 보내면 너무 오래걸려서 사용하지 않음
	 * @param categoryId
	 * @return
	 */
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
