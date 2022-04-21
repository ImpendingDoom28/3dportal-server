package ru.itis.threedportalserver.services.interfaces;

import ru.itis.threedportalserver.dtos.ProfileDto;

public interface ProfileService {

    ProfileDto getUserProfileById(Long id);
}
