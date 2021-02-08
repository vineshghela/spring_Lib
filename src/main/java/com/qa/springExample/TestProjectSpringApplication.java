package com.qa.springExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TestProjectSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestProjectSpringApplication.class, args);
//		ApplicationContext beanBag =SpringApplication.run(TestProjectSpringApplication.class, args);
//		// Not how you use beans but just for a demo to show how it works
////		String messageBean = beanBag.getBean(String.class); //1
////		System.out.println("Spring Bean: " + messageBean  );//1
////		System.out.println(beanBag.getBean("message2",String.class));//2
////		
//		//3 - you can see that thew same time is called becuase its a singelton.
//		System.out.println(beanBag.getBean("message",String.class));
//		System.out.println(beanBag.getBean("message",String.class));
//		System.out.println(beanBag.getBean("message",String.class));
//		
//		snip 1
	}
	
	// what important to note if when the application run it looks for marked methods. such as
	// bean
	//config..
//	in the background it is bascially doing this
	//snip 1
//	List<String> beans = new ArrayList<>();
//	AppConfig config = new AppConfig();
//	beans.add(config.message);
//	beans.add(config.message); // not the same as above its new 
//	beans.add(config.message2);
//	System.out.println(beans); // different because we are not using spring.
}
