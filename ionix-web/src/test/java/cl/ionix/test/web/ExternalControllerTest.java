package cl.ionix.test.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.Setter;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExternalControllerTest {

	@Setter
	@Autowired
	private MockMvc mock;
	
	@Test
	@Order(1)
	@DisplayName("Test de inyección de dependencia UserRepository")
	public void search_Success() throws Exception  {
		mock.perform(post("/externals/?param=1-9"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.registerCount", equalTo(3)))
			.andDo(print());
	}

	@Test
	@Order(2)
	@DisplayName("Test de inyección de dependencia UserRepository")
	public void search_Fail1() throws Exception  {
		mock.perform(post("/externals"))
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("$.errors[0].code", containsString("Required request parameter 'param'")))
			.andDo(print());
	}
}
