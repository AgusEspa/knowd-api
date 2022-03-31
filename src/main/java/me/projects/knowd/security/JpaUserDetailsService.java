package me.projects.knowd.security;

import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.repositories.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userRepository;

    public JpaUserDetailsService(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {

        UserEntity fetchedUser = userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UserEntityNotFoundException(emailAddress));

        return new SecurityUser(fetchedUser);
    }
}
