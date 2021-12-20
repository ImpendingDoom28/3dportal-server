package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.forms.RegisterForm;
import ru.itis.threedportalserver.models.User;
import ru.itis.threedportalserver.repositories.UsersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UsersRepository usersRepository;

    @Override
    public boolean registerUser(RegisterForm registerForm) {
        Optional<User> foundUser = usersRepository.findByEmail(registerForm.getEmail());

        if (!foundUser.isPresent()) {
            User newRegisteredUser = User.builder()
                    .password(UtilsService.hashSHA256(registerForm.getPassword()))
                    .email(registerForm.getEmail())
                    .build();

            User successUser = usersRepository.save(newRegisteredUser);

            return successUser.getId() != null;
        }
        throw new IllegalArgumentException(
                ExceptionStrings.USER_WITH_EMAIL_ALREADY_EXIST(registerForm.getEmail())
        );
    }
}
