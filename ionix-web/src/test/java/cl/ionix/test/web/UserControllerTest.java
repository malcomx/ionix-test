package cl.ionix.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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
//import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import com.github.javafaker.Faker;

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
	@Rollback // Descomentar para probar
//	@Commit // Descomentar para cargar datos persistentes en la tabla 
	@Order(2)
	@DisplayName("Test Create User full fill (Success)")
	public void create_Success_FullFill() throws Exception{
		// Obtiene el payload del request
		String body = dataUser.getStringObject();
		mock.perform(
			post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.description", equalTo("OK")))
			.andDo(print());
	}

	@Test
	@Rollback// Descomentar para probar
//	@Commit // Descomentar para cargar datos persistentes en la tabla 
	@Order(3)
	@DisplayName("Test Create User without phone (Success)")
	public void create_Success_WithoutPhone() throws Exception{
		// Obtiene el payload del request
		UserRequest request = dataUser.fillRequest();
		request.setPhone(null);
		String body = dataUser.getStringObject(request);
		mock.perform(
			post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.description", equalTo("OK")))
			.andDo(print());
	}
	
	/**
	 * *********** Casos de validacion JSR-303 **************
	 * 4) name: Case required
	 * 5) username: Case required
	 * 6) email: Case required
	 * 7) email: Case invalid format
	 * 8) email: Case email already
	 * */
	@Test
	@Rollback
	@Order(4)
	@DisplayName("Test Create User: name required (Fails)")
	public void create_Fail_NameRequired() throws Exception {
		// Obtiene el payload del request
		UserRequest request = dataUser.fillRequest();
		// Setea a null el nombre
		request.setName(null);
		// Obtiene el body en formato json
		String body = dataUser.getStringObject(request);
		// Realiza la peticion
		mock.perform(
			post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
			.andExpect(jsonPath("$.description", equalTo("ERROR")))
			.andExpect(jsonPath("$.errors[0].code", equalTo("user.name.required")))
			.andDo(print());
	}

	@Test
	@Rollback
	@Order(5)
	@DisplayName("Test Create User: Username required (Fails)")
	public void create_Fail_UsernameRequired() throws Exception {
		// Obtiene el payload del request
		UserRequest request = dataUser.fillRequest();
		// Setea a null el nombre de usuario
		request.setUsername(null);
		// Obtiene el body en formato json
		String body = dataUser.getStringObject(request);
		// Realiza la peticion
		mock.perform(
			post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
			.andExpect(jsonPath("$.description", equalTo("ERROR")))
			.andExpect(jsonPath("$.errors[0].code", equalTo("user.username.required")))
			.andDo(print());
	}

	@Test
	@Rollback
	@Order(6)
	@DisplayName("Test Create User: email required (Fails)")
	public void create_Fail_EmailRequired() throws Exception {
		// Obtiene el payload del request
		UserRequest request = dataUser.fillRequest();
		// Setea a null el email
		request.setEmail(null);
		// Obtiene el body en formato json
		String body = dataUser.getStringObject(request);
		// Realiza la peticion
		mock.perform(
			post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
			.andExpect(jsonPath("$.description", equalTo("ERROR")))
			.andExpect(jsonPath("$.errors[0].code", equalTo("user.email.required")))
			.andDo(print());
	}
	
	@Test
	@Rollback
	@Order(7)
	@DisplayName("Test Create User: email invalid (Fails)")
	public void create_Fail_EmailInvalid() throws Exception {
		// Obtiene el payload del request
		UserRequest request = dataUser.fillRequest();
		// Modifca el email
		request.setEmail(StringUtils.remove(request.getEmail(), "@"));
		// Obtiene el body en formato json
		String body = dataUser.getStringObject(request);
		// Realiza la peticion
		mock.perform(
			post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
			.andExpect(jsonPath("$.description", equalTo("ERROR")))
			.andExpect(jsonPath("$.errors[0].code", equalTo("user.email.not-valid")))
			.andDo(print());
	}
	
	@Test
	@Rollback
	@Order(8)
	@DisplayName("Test Create User: email duplicated (Fails)")
	public void create_Fail_EmailDuplicate() throws Exception {
		// Consulta los usuario registrados en db
		UserEntity userExists = this.repository.findAll().stream().findAny().orElse(null);
		if(null != userExists) {
			// Obtiene el payload del request
			UserRequest request = dataUser.fillRequest();
			// Setea un email existente
			request.setEmail(userExists.getEmail());
			// Obtiene el body en formato json
			String body = dataUser.getStringObject(request);
			// Realiza la peticion
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
			log.warn("******************* Caso de prueba no puede comprobarse, debido a la falta de datos en la tabla IONIX_USER");
		}
	}
	
	/*  */
	@Test
	@Order(9)
	@DisplayName("Test Find All")
	public void findAll() throws Exception {
		mock.perform(get("/users"))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	@Order(10)
	@DisplayName("Test Find user by email (Success)")
	public void findByEmail_Success() throws Exception {
		UserEntity user = this.repository.findAll().stream().findAny().orElse(null);
		if(null != user) {
			mock.perform(get("/users/" + user.getEmail()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result.email", equalTo(user.getEmail())))
				.andDo(print());
		} else {
			log.warn("******************* Caso de prueba no puede comprobarse, debido a la falta de datos en la tabla IONIX_USER");
		}
	}
	
	@Test
	@Order(11)
	@DisplayName("Test Find user by email: email invalido (Fail)")
	public void findByEmail_Fail1() throws Exception {
		mock.perform(get("/users/correo.invalido.gmail.com"))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.errors[0].code", equalTo("user.email.not-valid")))
			.andDo(print());
	}

	@Test
	@Order(12)
	@DisplayName("Test Find user by email: email no existe en db (Fail)")
	public void findByEmail_Fail2() throws Exception {
		Faker faker = new Faker();
		mock.perform(get("/users/" + faker.internet().emailAddress(faker.name().username() + "-" + RandomUtils.nextInt())))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.errors[0].code", equalTo("user.email.not-exists")))
			.andDo(print());
	}
}
