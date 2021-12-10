package ru.itis.threedportalserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.threedportalserver.models.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
