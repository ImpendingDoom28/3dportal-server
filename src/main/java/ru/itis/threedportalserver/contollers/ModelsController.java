package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.models.MessageTypes;
import ru.itis.threedportalserver.models.ModelFile;
import ru.itis.threedportalserver.services.ModelsService;

@RestController
@RequiredArgsConstructor
public class ModelsController {

    private final ModelsService modelsService;

    @PostMapping(value = "/api/models")
    @CrossOrigin(origins = "*")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadModel(
            @RequestParam("model") MultipartFile file,
            @RequestParam("givenName") String givenName
    ) {
        modelsService.saveModel(file, givenName);
        return ResponseEntity.ok(
                MessageDto.builder()
                        .message("Successfully loaded model")
                        .type(MessageTypes.SUCCESS)
                        .build()
        );
    }


    @GetMapping(value = "/api/models")
    @CrossOrigin(origins = "*")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getModels() {
        ModelFile[] modelFiles = modelsService.getModels();
        return ResponseEntity.ok(
                MessageDto.builder()
                        .message("Successfully loaded model")
                        .type(MessageTypes.SUCCESS)
                        .build()
        );
    }
}
