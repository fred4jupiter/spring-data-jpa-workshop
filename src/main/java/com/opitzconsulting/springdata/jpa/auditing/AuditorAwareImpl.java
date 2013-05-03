package com.opitzconsulting.springdata.jpa.auditing;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    public static final String USERNAME_FOR_SYSTEM_USER = "Fred";

    @Override
    public String getCurrentAuditor() {
        // Normally this place would return the current logged in username or user.
        return USERNAME_FOR_SYSTEM_USER;
    }
}
