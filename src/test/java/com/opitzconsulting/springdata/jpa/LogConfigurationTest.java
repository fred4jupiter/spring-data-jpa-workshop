package com.opitzconsulting.springdata.jpa;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * This is not real test. Simple for checking the logback configuration.
 */
public class LogConfigurationTest {

    @Test
    public void printLogbackConfiguration() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);
    }
}
