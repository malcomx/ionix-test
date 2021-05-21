package cl.ionix.test.web.model.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import cl.ionix.test.web.annotation.EmailDuplicate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString(callSuper = true)
public class UserRequest implements Serializable {

	private static final long serialVersionUID = 1436911532821645450L;
	
	@NotBlank(message = "user.name.required")
	private String name;
	@NotBlank(message = "user.username.required")
	private String username;
	@NotBlank(message = "user.email.required")
	@Email(message = "user.email.not-valid")
	@EmailDuplicate(message = "user.email.exists")
	private String email;
	private String phone;
}
