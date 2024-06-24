package tabling.server.json;

import lombok.Data;

@Data
public class JsonDTO {
	protected String type; // select, update, insert, delete
	private String msg;
}
