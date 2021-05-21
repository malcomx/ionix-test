package cl.ionix.test.web.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.ionix.test.repository.UserRepository;
import cl.ionix.test.repository.entity.UserEntity;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailDuplicateValidator implements ConstraintValidator<EmailDuplicate, String> {

	@Setter
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = false;
		try {
			// Consulta el usuario x email
			UserEntity entity = this.userRepository.findByEmail(value);
			// Valida la existencia del email en la db
			if(null == entity) {
				// Email no se encuentra registrado
				isValid = true;
			}
		} catch (Exception ex) {
			log.warn("Error consultando duplicidad de email: {} - {}", value, ex.getMessage());
		}
		
		return isValid;
	}

}
