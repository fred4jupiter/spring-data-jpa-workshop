package com.opitzconsulting.springdata.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application specific Java configuration.
 */
@Configuration
@EnableJpaRepositories
@Import(InfrastructureConfig.class)
public class ApplicationConfig {

}
