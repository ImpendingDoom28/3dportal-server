package ru.itis.threedportalserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.threedportalserver.models.ModelFile;

@Repository
public interface ModelFileRepository extends JpaRepository<ModelFile, Long> {
}
