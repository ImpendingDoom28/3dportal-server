package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.dtos.ModelFileDto;
import ru.itis.threedportalserver.models.MessageTypes;
import ru.itis.threedportalserver.models.ModelFile;
import ru.itis.threedportalserver.services.interfaces.ModelsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ModelsController {

    private final ModelsService modelsService;

    @PostMapping(value = "/api/models")
    @CrossOrigin(origins = "*")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadModel(
            @RequestParam("model") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("givenName") String givenName,
            @RequestParam("lastModified") String lastModified
    ) {
        modelsService.saveModel(
                file, userId, givenName, lastModified
        );
        return ResponseEntity.ok(
                MessageDto.builder()
                        .message("Successfully loaded model")
                        .type(MessageTypes.SUCCESS)
                        .build()
        );
    }


    @GetMapping(value = "/api/models")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getModels() {
        List<ModelFileDto> modelFiles = modelsService.getModels();
        return ResponseEntity.ok(
                MessageDto.builder()
                        .message("Successfully loaded model")
                        .type(MessageTypes.SUCCESS)
                        .build()
        );
    }

    @GetMapping(value = "/api/models/{userId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getModelsByUserId(
            @PathVariable Long userId
    ) {
        List<ModelFileDto> modelFiles = modelsService.getModelsByUserId(userId);
        return ResponseEntity.ok(modelFiles);
    }
}
