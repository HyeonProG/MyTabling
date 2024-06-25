package tabling.request;

public class LocationRequest {
	private String urlStr;

	public LocationRequest() {
		urlStr = "http://" + Request.getIp() + ":8080/location";
	}

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
