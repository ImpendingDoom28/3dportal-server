package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.dtos.PortalUserDto;
import ru.itis.threedportalserver.forms.LoginForm;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.repositories.PortalUsersRepository;
import ru.itis.threedportalserver.services.interfaces.LoginService;
import ru.itis.threedportalserver.services.interfaces.TokenService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final PortalUsersRepository portalUsersRepository;
    private final TokenService tokenService;

    @Override
    public LoginDto login(LoginForm loginForm) {
        Optional<PortalUser> foundUser = portalUsersRepository.findByEmail(loginForm.getEmail());
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();
            if (portalUser.getPassword().equals(UtilsService.hashSHA256(loginForm.getPassword()))) {
                return tokenService.generateTokens(PortalUserDto.from(portalUser));
            }
            throw new IllegalArgumentException(ExceptionStrings.YOUR_EMAIL_PASSWORD_IS_INCORRECT);
        }
        throw new IllegalArgumentException(ExceptionStrings.YOUR_EMAIL_PASSWORD_IS_INCORRECT);
    }
}
