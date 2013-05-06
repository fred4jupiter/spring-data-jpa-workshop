package com.opitzconsulting.springdata.jpa;

import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;
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
    public void bootstrapAppFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/app-config.xml");
        assertThat(context, is(notNullValue()));
        assertThat(context.getBean(CustomerRepository.class), is(notNullValue()));
    }
}
