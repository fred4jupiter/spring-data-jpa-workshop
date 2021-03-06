package com.opitzconsulting.springdata.jpa.auditing;

import com.opitzconsulting.springdata.jpa.domain.User;
import com.opitzconsulting.springdata.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

/**
 * Application specific implementation to return the current user.
 *
 * <p>NOTE: This is only a dummy implementation. Usually you would use a security framework to return this.</p>
 */
public class AuditorAwareImpl implements AuditorAware<User> {

    public static final String USERNAME_FOR_SYSTEM_USER = "Fred";

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentAuditor() {
        return findOrCreateUserBy(USERNAME_FOR_SYSTEM_USER);
    }

    private User findOrCreateUserBy(String username) {
        User fred = userRepository.findByUsername(username);
        if (fred != null) {
            return fred;
        } else {
            return userRepository.saveAndFlush(new User(username));
        }
    }
}
