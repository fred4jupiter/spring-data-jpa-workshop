package com.opitzconsulting.springdata.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan(basePackageClasses = CustomerFactory.class)
@Import(ApplicationConfig.class)
public class TestingConfig {

}
