package com.opitzconsulting.springdata.jpa;

import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;
import com.opitzconsulting.springdata.jpa.util.CustomerFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ApplicationConfigTest {

    @Test
    public void bootstrapAppFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        assertThat(context, is(notNullValue()));
        assertThat(context.getBean(CustomerRepository.class), is(notNullValue()));
    }

    @Test
    public void bootstrapAppFromTestingConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestingConfig.class);
        assertThat(context, is(notNullValue()));
        assertThat(context.getBean(CustomerFactory.class), is(notNullValue()));
    }

    @Test
    public void bootstrapAppFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/app-config.xml");
        assertThat(context, is(notNullValue()));
        assertThat(context.getBean(CustomerRepository.class), is(notNullValue()));
    }

    @Test
    public void bootstrapAppFromXml_Testing() {
        ApplicationContext context = new ClassPathXmlApplicationContext("test-app-config.xml");
        assertThat(context, is(notNullValue()));
        assertThat(context.getBean(CustomerFactory.class), is(notNullValue()));
    }
}
