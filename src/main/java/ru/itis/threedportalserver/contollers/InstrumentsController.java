package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.threedportalserver.dtos.InstrumentDto;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.dtos.ModelFileDto;
import ru.itis.threedportalserver.forms.InstrumentForm;
import ru.itis.threedportalserver.services.interfaces.InstrumentsService;
import ru.itis.threedportalserver.services.interfaces.ModelsService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class InstrumentsController {

    private final ModelsService modelsService;
    private final InstrumentsService instrumentsService;

    @GetMapping(value = "/api/instruments/models/model--{generatedName}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getModelByGeneratedName(
            @PathVariable String generatedName
    ) {
        ModelFileDto modelFileDto = modelsService.getModelByGeneratedName(generatedName);
        return ResponseEntity.ok(modelFileDto);
    }

    @PostMapping(value = "/api/instruments")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createInstrument(
            @RequestBody InstrumentForm instrumentForm
    ) {
        MessageDto messageDto = instrumentsService.createInstrument(instrumentForm);
        return ResponseEntity.ok(messageDto);
    }

    @GetMapping(value = "/api/instruments")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getInstrumentsByParams(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Boolean hosted
    ) {
        if(authorId != null) {
            return ResponseEntity.ok(instrumentsService.getInstrumentsByAuthorId(authorId));
        } else if(hosted != null) {
            return ResponseEntity.ok(instrumentsService.getInstrumentsByParams(hosted));
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PutMapping(value = "/api/instruments/{instrumentId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> changeInstrumentById(
            @PathVariable Long instrumentId,
            @RequestBody InstrumentDto instrumentDto
            ) {
        return ResponseEntity.ok(instrumentsService.changeInstrumentById(instrumentId, instrumentDto));
    }
}
