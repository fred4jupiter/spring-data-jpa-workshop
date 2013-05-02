package com.opitzconsulting.springdata.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Base class for integration tests with Spring.
 * 
 * @author michael
 * 
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-config.xml" })
public abstract class AbstractTestingBase extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	protected EntityManager entityManager;

}
