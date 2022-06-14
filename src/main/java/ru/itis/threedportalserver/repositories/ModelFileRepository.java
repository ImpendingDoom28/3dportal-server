package ru.itis.threedportalserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.threedportalserver.models.ModelFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelFileRepository extends JpaRepository<ModelFile, Long> {

    @Query(
            value = "SELECT * FROM public.model_file mf file WHERE mf.user_id = :userId",
            nativeQuery = true
    )
    Optional<List<ModelFile>> findByUserId(@Param("userId") Long userId);

    @Query(
            value = "SELECT * FROM public.model_file mf WHERE mf.generated_name = :generatedName",
            nativeQuery = true
    )
    Optional<ModelFile> findByGeneratedName(@Param("generatedName") String generatedName);
}
