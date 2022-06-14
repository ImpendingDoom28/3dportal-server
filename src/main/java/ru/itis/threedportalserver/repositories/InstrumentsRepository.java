package ru.itis.threedportalserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.threedportalserver.models.Instrument;

import java.util.List;

@Repository
public interface InstrumentsRepository extends JpaRepository<Instrument, Long> {

    @Query(
            value = "SELECT * FROM public.instrument i WHERE i.author_id = :authorId",
            nativeQuery = true
    )
    List<Instrument> findInstrumentsByAuthorId(
            @Param("authorId") Long authorId
    );

}
