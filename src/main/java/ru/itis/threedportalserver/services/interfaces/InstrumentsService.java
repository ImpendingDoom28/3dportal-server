package ru.itis.threedportalserver.services.interfaces;

import ru.itis.threedportalserver.dtos.InstrumentDto;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.forms.InstrumentForm;

import java.util.List;

public interface InstrumentsService {
    MessageDto createInstrument(InstrumentForm instrumentForm);

    List<InstrumentDto> getInstrumentsByAuthorId(Long authorId);

    InstrumentDto changeInstrumentById(Long instrumentId, InstrumentDto instrumentDto);

    List<InstrumentDto> getInstrumentsByParams(Boolean hosted);
}
