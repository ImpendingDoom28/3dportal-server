package ru.itis.threedportalserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.threedportalserver.models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
