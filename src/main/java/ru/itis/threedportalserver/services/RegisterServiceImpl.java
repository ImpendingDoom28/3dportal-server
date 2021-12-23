package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.forms.RegisterForm;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.repositories.UsersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UsersRepository usersRepository;

    @Override
    public boolean registerUser(RegisterForm registerForm) {
        Optional<PortalUser> foundUser = usersRepository.findByEmail(registerForm.getEmail());

        if (!foundUser.isPresent()) {
            PortalUser newRegisteredPortalUser = PortalUser.builder()
                    .password(UtilsService.hashSHA256(registerForm.getPassword()))
                    .email(registerForm.getEmail())
                    .build();

            PortalUser successPortalUser = usersRepository.save(newRegisteredPortalUser);

            return successPortalUser.getId() != null;
        }
        throw new IllegalArgumentException(
                ExceptionStrings.USER_WITH_EMAIL_ALREADY_EXIST(registerForm.getEmail())
        );
    }
}
