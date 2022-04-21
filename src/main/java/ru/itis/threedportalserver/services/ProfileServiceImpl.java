package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.dtos.ProfileDto;
import ru.itis.threedportalserver.models.Profile;
import ru.itis.threedportalserver.repositories.ProfileRepository;
import ru.itis.threedportalserver.services.interfaces.ProfileService;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileDto getUserProfileById(Long id) {
        Profile profile = profileRepository.getById(id);
        return ProfileDto.from(profile);
    }
}
