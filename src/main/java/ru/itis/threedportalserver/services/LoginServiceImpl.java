package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.dtos.UserDto;
import ru.itis.threedportalserver.forms.LoginForm;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.repositories.UsersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UsersRepository usersRepository;
    private final TokenService tokenService;

    @Override
    public LoginDto login(LoginForm loginForm) {
        Optional<PortalUser> foundUser = usersRepository.findByEmail(loginForm.getEmail());
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();
            if (portalUser.getPassword().equals(UtilsService.hashSHA256(loginForm.getPassword()))) {
                return tokenService.generateTokens(UserDto.from(portalUser));
            }
            throw new IllegalArgumentException(ExceptionStrings.USER_NOT_FOUND);
        }
        throw new IllegalArgumentException(ExceptionStrings.USER_NOT_FOUND);
    }
}
