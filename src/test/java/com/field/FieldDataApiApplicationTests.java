package com.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.field.resource.FieldController;

@SpringBootTest
class FieldDataApiApplicationTests {
	
	@Autowired
	private FieldController fieldController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(fieldController).isNotNull();
	}

}
