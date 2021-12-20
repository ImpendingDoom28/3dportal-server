package ru.itis.threedportalserver.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.models.User;
import ru.itis.threedportalserver.repositories.UsersRepository;

import java.util.Optional;

@Component("jwtUserDetailsImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> foundUser = usersRepository.findByEmail(email);
        if(foundUser.isPresent()) {
            User user = foundUser.get();
            return new UserDetailsImpl(user);
        } throw new UsernameNotFoundException(ExceptionStrings.USER_WITH_EMAIL_DOES_NOT_EXIST(email));
    }
}
