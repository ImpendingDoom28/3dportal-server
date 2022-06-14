package ru.itis.threedportalserver.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.repositories.PortalUsersRepository;

import java.util.Optional;

@Component("jwtUserDetailsImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PortalUsersRepository portalUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<PortalUser> foundUser = portalUsersRepository.findByEmail(email);
        if(foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();
            return new UserDetailsImpl(portalUser);
        } throw new UsernameNotFoundException(ExceptionStrings.USER_WITH_EMAIL_DOES_NOT_EXIST(email));
    }
}
