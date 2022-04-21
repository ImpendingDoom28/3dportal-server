package ru.itis.threedportalserver.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.dtos.ModelFileDto;

import java.util.List;

public interface ModelsService {

    void saveModel(
            MultipartFile file,
            Long userId,
            String givenName,
            String lastModified
    );

    List<ModelFileDto> getModelsByUserId(Long userId);
    List<ModelFileDto> getModels();
}
