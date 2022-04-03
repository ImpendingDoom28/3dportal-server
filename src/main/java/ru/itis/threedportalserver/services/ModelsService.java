package ru.itis.threedportalserver.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.models.ModelFile;

public interface ModelsService {

    void saveModel(MultipartFile file, String givenName);

    ModelFile[] getModels();
}
