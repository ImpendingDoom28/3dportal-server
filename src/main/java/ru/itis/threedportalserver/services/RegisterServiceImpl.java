package ru.itis.threedportalserver.services;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.forms.RegisterForm;
import ru.itis.threedportalserver.models.User;
import ru.itis.threedportalserver.repositories.UsersRepository;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UsersRepository usersRepository;

    @Override
    public boolean registerUser(RegisterForm registerForm) {
        User foundUser = usersRepository.findByEmail(registerForm.getEmail());

        if (foundUser == null) {
            User newRegisteredUser = User.builder()
                    .password(Hashing.sha256().hashString(registerForm.getPassword(), StandardCharsets.UTF_8).toString())
                    .email(registerForm.getEmail())
                    .build();

            User successUser = usersRepository.save(newRegisteredUser);

            return successUser.getId() != null;
        }
        throw new IllegalArgumentException("User with email '" + registerForm.getEmail() + "' exist!");
    }
}
