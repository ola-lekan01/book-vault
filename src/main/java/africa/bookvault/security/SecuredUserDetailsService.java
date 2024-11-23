package africa.bookvault.security;

import africa.bookvault.models.User;
import africa.bookvault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SecuredUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUserName(username)
                .map(SecuredUser::new)
                .orElse(null);
    }

    public Optional<User> findUserByUserName(String userName) {
        return userRepository
                .findByUserName(userName);
    }
}
