package junit.test;



import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTest {

	@Resource
	//private static IPersonService psersonservice;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {/*
		try {
			ApplicationContext act = new ClassPathXmlApplicationContext("config/applicationContext.xml");
			psersonservice = (IPersonService) act.getBean("personServer");
		} catch (Exception e) {
			throw e;
		}
	*/}

	@Test
	public void testSave() {/*
		Person person=new Person();
		person.setP_name("aaaa");
		psersonservice.save(person);
	*/}


}
