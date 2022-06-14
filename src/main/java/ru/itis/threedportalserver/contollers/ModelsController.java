package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.dtos.ModelFileDto;
import ru.itis.threedportalserver.models.MessageTypes;
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
            @RequestParam("modelFile") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("previewImageUrl") String previewImageUrl,
            @RequestParam("userId") Long userId,
            @RequestParam("givenName") String givenName,
            @RequestParam("lastModified") String lastModified
    ) {
        modelsService.saveModel(
                file, userId, givenName, lastModified, description, previewImageUrl
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
    public Page<ModelFileDto> getModels(
            Pageable pageable
    ) {
        return modelsService.getModels(pageable);
    }

    @GetMapping(value = "/api/models/{userId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getModelsByUserId(
            @PathVariable Long userId
    ) {
        List<ModelFileDto> modelFiles = modelsService.getModelsByUserId(userId);
        return ResponseEntity.ok(modelFiles);
    }

    @GetMapping(value = "/api/models/model--{generatedName}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getModelByGeneratedName(
            @PathVariable String generatedName
    ) {
        ModelFileDto modelFileDto = modelsService.getModelByGeneratedName(generatedName);
        return ResponseEntity.ok(modelFileDto);
    }
}
