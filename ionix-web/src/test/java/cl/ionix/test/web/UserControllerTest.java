package cl.ionix.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import cl.ionix.test.repository.UserRepository;
import cl.ionix.test.repository.entity.UserEntity;
import cl.ionix.test.web.model.request.UserRequest;
import cl.ionix.test.web.utils.PopulateUserTest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

	@Setter
	@Autowired
	private UserRepository repository;
	
	@Setter
	@Autowired
	private MockMvc mock;
	private static PopulateUserTest dataUser;
	
	@BeforeAll
	public static void init() {
		dataUser = new PopulateUserTest();
	}

	@Test
	@Order(1)
	@DisplayName("Test de inyección de dependencia UserRepository")
	public void contextLoads() throws NullPointerException {
		assertThat(repository).isNotNull();
	}
	
	@Test
	@Rollback
	@Order(2)
	@DisplayName("Test Create User (Success)")
	public void create_Success() throws Exception{
		// Obtiene el paylos del request
		String body = dataUser.getStringObject();
		
		mock.perform(
			post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.description", equalTo("OK")))
			.andDo(print())
		;
	}

	@Test
	@Rollback
	@Order(3)
	@DisplayName("Test Create User (Fails)")
	public void create_Fail_EmailExists() throws Exception {
		// Consulta los usuario registrados
		UserEntity userExists = this.repository.findAll().stream().findAny().orElse(null);
		if(null != userExists) {
			// Obtiene el paylos del request
			UserRequest request = dataUser.fillRequest();
			request.setEmail(userExists.getEmail());
			String body = dataUser.getStringObject(request);
			mock.perform(
					post("/users")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(body))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.description", equalTo("ERROR")))
					.andExpect(jsonPath("$.errors[0].code", equalTo("user.email.exists")))
					.andDo(print())
				;
		} else {
			// 
			log.warn("Caso de prueba no puede comprobarse, debido a la falta de datos en la tabla IONIX_USER");
		}
	}
}
