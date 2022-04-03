package ru.itis.threedportalserver.services;

import ru.itis.threedportalserver.dtos.ProfileDto;

public interface ProfileService {

    ProfileDto getUserProfileById(Long id);
}
