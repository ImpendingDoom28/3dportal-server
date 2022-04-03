package ru.itis.threedportalserver.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.models.ModelFile;

import java.util.Calendar;
import java.util.UUID;

@Service
public class ModelsServiceImpl implements ModelsService {

    @Override
    public void saveModel(MultipartFile file, String givenName) {
        ModelFile modelFile = ModelFile.builder()
                .givenName(givenName)
                .generatedName(UUID.randomUUID().toString())
                .uploadedName(file.getOriginalFilename())
                .uploadDate(Calendar.getInstance().getTimeInMillis())
                .build();
        System.out.println(modelFile);
    }

    @Override
    public ModelFile[] getModels() {
        return new ModelFile[0];
    }
}
