package com.qa.springExample;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TestProjectSpringApplicationTests {

	 @SuppressWarnings("static-access")
	    @Test
	    void contextLoads() {
		 TestProjectSpringApplication app = new TestProjectSpringApplication();
	        String[] args = {};
	        app.main(args);
	        assertThat(app).isInstanceOf(TestProjectSpringApplication.class);
	    }

}
