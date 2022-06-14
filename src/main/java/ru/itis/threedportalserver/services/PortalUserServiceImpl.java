package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.dtos.ApiKeyDto;
import ru.itis.threedportalserver.dtos.PortalUserDto;
import ru.itis.threedportalserver.forms.PortalUserForm;
import ru.itis.threedportalserver.models.ApiKey;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.models.PortalUserRole;
import ru.itis.threedportalserver.repositories.ApiKeysRepository;
import ru.itis.threedportalserver.repositories.PortalUsersRepository;
import ru.itis.threedportalserver.services.interfaces.ApiKeyService;
import ru.itis.threedportalserver.services.interfaces.PortalUserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortalUserServiceImpl implements PortalUserService {

    private final PortalUsersRepository portalUsersRepository;
    private final ApiKeysRepository apiKeysRepository;
    private final ApiKeyService apiKeyService;

    @Override
    public PortalUserDto changeUser(PortalUserForm portalUserForm) {
        Optional<PortalUser> foundUser = portalUsersRepository.findById(portalUserForm.getId());
        if(foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();
            if(portalUserForm.getRole() != null) {
                portalUser.setUserRole(portalUserForm.getRole());
            }
            PortalUser changedUser = portalUsersRepository.saveAndFlush(portalUser);
            return PortalUserDto.from(changedUser);
        } throw new IllegalArgumentException(
                ExceptionStrings.USER_WITH_ID_DOES_NOT_EXIST(String.valueOf(portalUserForm.getId()))
        );
    }

    @Override
    public ApiKeyDto getApiKey(Long userId) {
        Optional<PortalUser> foundUser = portalUsersRepository.findById(userId);
        if(foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();
            if (portalUser.getUserRole() == PortalUserRole.DEVELOPER) {
                Optional<ApiKey> foundKey = apiKeysRepository.findByUserId(userId);
                if (foundKey.isPresent()) {
                    return ApiKeyDto.builder()
                            .apiKey(foundKey.get().text)
                            .build();
                } else {
                    String generatedKey = apiKeyService.generateApiKey();

                    ApiKey savedApiKey = apiKeysRepository.saveAndFlush(ApiKey.builder()
                            .text(generatedKey)
                            .userId(portalUser)
                            .build()
                    );

                    return ApiKeyDto.builder()
                            .apiKey(savedApiKey.text)
                            .build();
                }
            }
        }
        throw new IllegalArgumentException(
                ExceptionStrings.USER_WITH_ID_DOES_NOT_EXIST(String.valueOf(userId))
        );
    }

}
