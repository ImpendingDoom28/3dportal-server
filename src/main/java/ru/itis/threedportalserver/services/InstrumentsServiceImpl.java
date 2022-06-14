package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.dtos.InstrumentDto;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.dtos.PortalUserDto;
import ru.itis.threedportalserver.forms.InstrumentForm;
import ru.itis.threedportalserver.models.Instrument;
import ru.itis.threedportalserver.models.MessageTypes;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.repositories.InstrumentsRepository;
import ru.itis.threedportalserver.repositories.PortalUsersRepository;
import ru.itis.threedportalserver.services.interfaces.InstrumentsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentsServiceImpl implements InstrumentsService {

    private final InstrumentsRepository instrumentsRepository;
    private final PortalUsersRepository usersRepository;

    @Override
    public MessageDto createInstrument(InstrumentForm instrumentForm) {
        Optional<PortalUser> foundUser = usersRepository.findById(instrumentForm.getAuthorId());
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();

            instrumentsRepository.save(
                    Instrument.builder()
                            .description(instrumentForm.getDescription())
                            .name(instrumentForm.getName())
                            .authorId(portalUser)
                            .build()
            );
            return MessageDto.builder()
                    .type(MessageTypes.SUCCESS)
                    .message("Инструмент успешно создан!")
                    .build();
        }
        throw new IllegalArgumentException(
                ExceptionStrings.USER_WITH_ID_DOES_NOT_EXIST(String.valueOf(instrumentForm.getAuthorId()))
        );
    }

    @Override
    public List<InstrumentDto> getInstrumentsByAuthorId(Long authorId) {
        Optional<PortalUser> foundUser = usersRepository.findById(authorId);
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();

            List<Instrument> foundInstruments = instrumentsRepository.findInstrumentsByAuthorId(authorId);

            return foundInstruments.stream()
                    .map(instrument -> InstrumentDto.builder()
                            .author(PortalUserDto.from(portalUser))
                            .id(instrument.getId())
                            .description(instrument.getDescription())
                            .hostUrl(instrument.getHostUrl())
                            .name(instrument.getName())
                            .build())
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException(
                ExceptionStrings.USER_WITH_ID_DOES_NOT_EXIST(String.valueOf(authorId))
        );
    }

    @Override
    public InstrumentDto changeInstrumentById(Long instrumentId, InstrumentDto instrumentDto) {
        Optional<Instrument> foundInstrument = instrumentsRepository.findById(instrumentId);
        if (foundInstrument.isPresent()) {
            Instrument instrument = foundInstrument.get();

            Instrument savedInstrument = instrumentsRepository.saveAndFlush(
                    Instrument.builder()
                            .id(instrument.getId())
                            .name(instrumentDto.getName())
                            .description(instrumentDto.getDescription())
                            .hostUrl(instrumentDto.getHostUrl())
                            .authorId(instrument.getAuthorId())
                            .build()
            );
            return InstrumentDto.builder()
                    .name(savedInstrument.getName())
                    .description(savedInstrument.getDescription())
                    .hostUrl(savedInstrument.getHostUrl())
                    .id(savedInstrument.getId())
                    .author(PortalUserDto.from(savedInstrument.getAuthorId()))
                    .build();
        }
        throw new IllegalArgumentException(
                ExceptionStrings.INSTRUMENT_WITH_ID_DOES_NOT_EXIST(String.valueOf(instrumentId))
        );
    }

    @Override
    public List<InstrumentDto> getInstrumentsByParams(Boolean hosted) {
        List<Instrument> instruments = instrumentsRepository.findAll();

        return instruments.stream()
                .filter(instrument -> instrument.getHostUrl() != null)
                .map(instrument -> InstrumentDto.builder()
                        .id(instrument.getId())
                        .author(PortalUserDto.from(instrument.getAuthorId()))
                        .name(instrument.getName())
                        .hostUrl(instrument.getHostUrl())
                        .description(instrument.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
