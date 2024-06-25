package tabling.request;

public class LocationRequest {
	private String urlStr;

	public LocationRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/location";
	}

	/**
	 * 매번 요청을 보내면 너무 오래걸려서 사용하지 않음
	 * @param locationId
	 * @return
	 */
	public String getLocationName(int locationId) {
		String str = null;
		try {
			String selectUrl = urlStr + "/" + locationId;
			str = Request.getRequest(selectUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

}
