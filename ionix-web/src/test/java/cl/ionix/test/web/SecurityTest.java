package cl.ionix.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.ionix.test.web.exception.IonixWebException;
import cl.ionix.test.web.utils.IonixSecurityUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecurityTest {
	
	@Setter
	@Autowired
	private IonixSecurityUtils securityUtils;

	private static String key = "ionix123456";
	
	@Test
	@Order(1)
	@DisplayName("Test de inyección de dependencia IonixSecurityUtils")
	public void contextLoads() throws NullPointerException {
		assertThat(securityUtils).isNotNull();
	}
	
	@Test
	@Order(2)
	@DisplayName("Test Fail: Value is NULL")
	public void cipher_FailValueNull() {
		assertThatThrownBy(() -> securityUtils.cipher(null, key))
			.isInstanceOf(IonixWebException.class)
			.hasMessageContaining("Value is null");
	}
	
	@Test
	@Order(3)
	@DisplayName("Test Fail: Key is NULL")
	public void cipher_FailKeyNull() {
		assertThatThrownBy(() -> securityUtils.cipher("value", null))
			.isInstanceOf(IonixWebException.class)
			.hasMessageContaining("Key is null");
	}
	
	@Test
	@Order(4)
	@DisplayName("Test Fail: Key is invalid")
	public void cipher_FailKeyInvalid() {
		assertThatThrownBy(() -> securityUtils.cipher("value", ""))
			.isInstanceOf(IonixWebException.class)
			.hasMessageContaining("Wrong key size");
	}

	@Test
	@Order(5)
	@DisplayName("Test Success")
	public void cipher_Success() {
		assertThat(securityUtils.cipher("1-9", key))
			.as("Error no controlado!! REVISAR ESTE CASO")
			.decodedAsBase64();
	}

	@AfterAll
	public static void done() {
		log.info("***************** Ejecutados todos los casos de IonixSecurityUtils *********************");
	}
}
