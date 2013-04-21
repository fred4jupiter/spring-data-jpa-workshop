package de.opitz.spring.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.opitzconsulting.spring.repository.CustomerRepository;

/**
 * Base class for integration tests with Spring.
 * 
 * @author michael
 * 
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-config.xml" })
public abstract class AbstractTestingBase extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	protected CustomerRepository customerRepository;

	@PersistenceContext
	protected EntityManager entityManager;

}
