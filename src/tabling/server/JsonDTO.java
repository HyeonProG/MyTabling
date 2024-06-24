package tabling.server;

import lombok.Data;

@Data
public class JsonDTO {
	private String type; // select, update, insert, delete
	private String msg;
}
