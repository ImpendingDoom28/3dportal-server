package ru.itis.threedportalserver.services.interfaces;

import ru.itis.threedportalserver.dtos.ApiKeyDto;
import ru.itis.threedportalserver.dtos.PortalUserDto;
import ru.itis.threedportalserver.forms.PortalUserForm;

public interface PortalUserService {
    PortalUserDto changeUser(PortalUserForm portalUserForm);

    ApiKeyDto getApiKey(Long userId);
}
