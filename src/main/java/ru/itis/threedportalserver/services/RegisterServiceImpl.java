package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.forms.RegisterForm;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.models.Profile;
import ru.itis.threedportalserver.repositories.ProfileRepository;
import ru.itis.threedportalserver.repositories.UsersRepository;
import ru.itis.threedportalserver.services.interfaces.RegisterService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UsersRepository usersRepository;
    private final ProfileRepository profileRepository;

    @Override
    public boolean registerUser(RegisterForm registerForm) {
        Optional<PortalUser> foundUser = usersRepository.findByEmail(registerForm.getEmail());

        if (!foundUser.isPresent()) {
            PortalUser newRegisteredPortalUser = PortalUser.builder()
                    .password(UtilsService.hashSHA256(registerForm.getPassword()))
                    .email(registerForm.getEmail())
                    .build();

            PortalUser successPortalUser = usersRepository.save(newRegisteredPortalUser);
            Profile profile = Profile.builder()
                    .id(successPortalUser.getId())
                    .build();
            Profile savedProfile = profileRepository.save(profile);
            System.out.println(savedProfile);

            successPortalUser.setProfile(savedProfile);

            System.out.println(successPortalUser);

            usersRepository.save(successPortalUser);

            return successPortalUser.getId() != null;
        }
        throw new IllegalArgumentException(
                ExceptionStrings.USER_WITH_EMAIL_ALREADY_EXIST(registerForm.getEmail())
        );
    }
}
