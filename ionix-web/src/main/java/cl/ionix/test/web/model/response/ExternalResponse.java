package cl.ionix.test.web.model.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExternalResponse implements Serializable {
	
	private static final long serialVersionUID = 1831333185929385199L;
	
	private int responseCode;
	private String description;
	private Result result;

	@Data
	public static class Result {
		private List<Items> items;
	}
	@Data
	public static class Items {
		private String name;
		private Detail detail;
	}
	@Data
	public static class Detail {
		private String email;
		@JsonProperty("phone_number")
		private String phoneNumber;
	}
}
