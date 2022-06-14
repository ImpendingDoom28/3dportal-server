package ru.itis.threedportalserver.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.dtos.ModelFileDto;

import java.util.List;

public interface ModelsService {

    void saveModel(
            MultipartFile file,
            Long userId,
            String givenName,
            String lastModified,
            String description,
            String previewImageUrl
    );

    ModelFileDto getModelByGeneratedName(String modelId);

    List<ModelFileDto> getModelsByUserId(Long userId);
    Page<ModelFileDto> getModels(Pageable pageable);
}
