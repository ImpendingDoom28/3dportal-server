package ru.itis.threedportalserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.threedportalserver.models.PortalUser;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<PortalUser, Long> {

    Optional<PortalUser> findByEmail(String email);
}
