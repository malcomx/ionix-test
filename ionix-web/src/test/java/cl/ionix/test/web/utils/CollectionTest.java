package cl.ionix.test.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CollectionTest {

	@Test
	public void map() {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1,  "Prueba");
		map.put(2,  "Prueba2");
		map.put(3,  "Prueba3");
		System.out.println(map.toString());
	}
}
