package cl.ionix.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import cl.ionix.test.repository.UserRepository;
import cl.ionix.test.repository.entity.UserEntity;
import cl.ionix.test.web.utils.PopulateUserTest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

	@Setter
	@Autowired
	private UserRepository repository;
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
	@Order(2)
	@DisplayName("Test fallido de creación de usuario en DB (name is null)")
	public void createUser_FailsNameNull() {
		UserEntity entity = dataUser.fillEntity();
		entity.setName(null); // Seteo a null para validar el caso
		assertThatThrownBy(() -> { repository.save(entity); });
	}

	@Test
	@Order(3)
	@DisplayName("Test fallido de creación de usuario en DB (username is null)")
	public void createUser_FailsUsernameNull() {
		UserEntity entity = dataUser.fillEntity();
		entity.setUsername(null); // Seteo a null para validar el caso
		assertThatThrownBy(() -> { repository.save(entity); });
	}

	@Test
	@Order(4)
	@DisplayName("Test fallido de creación de usuario en DB (email is null)")
	public void createUser_FailsEmailNull() {
		UserEntity entity = dataUser.fillEntity();
		entity.setEmail(null); // Seteo a null para validar el caso
		assertThatThrownBy(() -> { repository.save(entity); });
	}

	@Rollback
	@Test
	@Order(5)
	@DisplayName("Test satistafcatorio 1 de creación de usuario en DB (full fields)")
	public void createUser_Success1() {
		UserEntity entity = dataUser.fillEntity();
		repository.save(entity);
		assertThat(entity.getId() > 0).isTrue();
	}

	@Rollback
	@Test
	@Order(6)
	@DisplayName("Test satistafcatorio 2 de creación de usuario en DB (with phone null)")
	public void createUser_Success2() {
		UserEntity entity = dataUser.fillEntity();
		entity.setPhone(null);
		repository.save(entity);
		assertThat(entity.getId() > 0).isTrue();
	}
	
	@AfterAll
	public static void done() {
		log.info("***************** Ejecutados todos los casos de User Repository *********************");
	}
}
