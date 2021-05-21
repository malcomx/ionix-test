package cl.ionix.test.web.model.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class DataWebResponse implements Serializable {

	private static final long serialVersionUID = 880536444685975885L;
	
	private int responseCode;
	private String description;
	private long elapsedTime;
	@JsonInclude(Include.NON_NULL)
	private Object result;
	@JsonInclude(Include.NON_NULL)
	private List<ErrorResponse> errors;
	
	public DataWebResponse(Object result) {
		this.responseCode = 0;
		this.description = "OK";
		this.result = result;
	}
	
	public DataWebResponse(int responseCode, List<ErrorResponse> errors) {
		this.responseCode = responseCode;
		this.description = "ERROR";
		this.errors = errors;
	}
	
	public DataWebResponse(int responseCode, String description) {
		this.responseCode = responseCode;
		this.description = description;
	}
}
