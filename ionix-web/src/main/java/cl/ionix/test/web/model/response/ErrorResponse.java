package cl.ionix.test.web.model.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 6891591157101899698L;
	
	private String code;
	private String description;
}
