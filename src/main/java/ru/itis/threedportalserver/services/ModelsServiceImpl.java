package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.dtos.ModelFileDto;
import ru.itis.threedportalserver.dtos.PortalUserDto;
import ru.itis.threedportalserver.models.ModelFile;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.repositories.BucketFileRepository;
import ru.itis.threedportalserver.repositories.ModelFileRepository;
import ru.itis.threedportalserver.repositories.PortalUsersRepository;
import ru.itis.threedportalserver.services.interfaces.ModelsService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ModelsServiceImpl implements ModelsService {

    private final ModelFileRepository modelFileRepository;
    private final PortalUsersRepository portalUsersRepository;
    private final BucketFileRepository bucketFileRepository;

    private List<ModelFileDto> getModelFileDtosFrom(List<ModelFile> modelFiles) {
        List<ModelFileDto> mappedModelFiles = new ArrayList<>();

        modelFiles.forEach((modelFile) -> {
            String presignedModelUrl = bucketFileRepository.getPreSignedUrlFromModelFile(modelFile);

            mappedModelFiles.add(
                    ModelFileDto.builder()
                            .modelUrl(presignedModelUrl)
                            .description(modelFile.getDescription())
                            .previewImageUrl(modelFile.getPreviewImageUrl())
                            .uploadDate(modelFile.getUploadDate())
                            .generatedName(modelFile.getGeneratedName())
                            .givenName(modelFile.getGivenName())
                            .mimeType(modelFile.getMimeType())
                            .lastModified(modelFile.getLastModified())
                            .user(PortalUserDto.from(modelFile.getUserId()))
                            .build()
            );
        });
        return mappedModelFiles;
    }

    private Page<ModelFileDto> getModelFileDtosFrom(Page<ModelFile> modelFilesPage) {
        return modelFilesPage
                .map(modelFile -> {
                    String presignedModelUrl = bucketFileRepository.getPreSignedUrlFromModelFile(modelFile);
                    return ModelFileDto.builder()
                            .modelUrl(presignedModelUrl)
                            .description(modelFile.getDescription())
                            .previewImageUrl(modelFile.getPreviewImageUrl())
                            .uploadDate(modelFile.getUploadDate())
                            .generatedName(modelFile.getGeneratedName())
                            .givenName(modelFile.getGivenName())
                            .mimeType(modelFile.getMimeType())
                            .lastModified(modelFile.getLastModified())
                            .user(PortalUserDto.from(modelFile.getUserId()))
                            .build();
                });
    }

    @Override
    public void saveModel(
            MultipartFile file,
            Long userId,
            String givenName,
            String lastModified,
            String description,
            String previewImageUrl
    ) {
        Optional<PortalUser> foundUser = portalUsersRepository.findById(userId);
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();

            String generatedName = UUID.randomUUID().toString();
            String entityTag = bucketFileRepository.uploadFile(
                    file,
                    generatedName,
                    portalUser.getEmail()
            );

            String originalFileName = file.getOriginalFilename();
            String mimeType = originalFileName.substring(
                    originalFileName.lastIndexOf(".")
            );

            ModelFile modelFile = ModelFile.builder()
                    .description(description)
                    .previewImageUrl(previewImageUrl)
                    .givenName(givenName)
                    .generatedName(generatedName)
                    .userId(PortalUser.builder().id(userId).build())
                    .entityTag(entityTag)
                    .mimeType(mimeType)
                    .originalFileName(originalFileName)
                    .lastModified(lastModified)
                    .uploadDate(Calendar.getInstance().getTimeInMillis())
                    .build();

            modelFileRepository.save(modelFile);
        } else {
            throw new IllegalArgumentException("No such user exist");
        }
    }

    @Override
    public ModelFileDto getModelByGeneratedName(String givenName) {
        Optional<ModelFile> foundFile = modelFileRepository.findByGeneratedName(givenName);
        if(foundFile.isPresent()) {
            ModelFile modelFile = foundFile.get();
            String presignedModelUrl = bucketFileRepository.getPreSignedUrlFromModelFile(modelFile);
            return ModelFileDto.builder()
                    .modelUrl(presignedModelUrl)
                    .description(modelFile.getDescription())
                    .givenName(modelFile.getGivenName())
                    .user(PortalUserDto.from(modelFile.getUserId()))
                    .uploadDate(modelFile.getUploadDate())
                    .lastModified(modelFile.getLastModified())
                    .generatedName(modelFile.getGeneratedName())
                    .mimeType(modelFile.getMimeType())
                    .previewImageUrl(modelFile.getPreviewImageUrl())
                    .build();
        }
        throw new IllegalArgumentException("No such model exist");
    }

    @Override
    public Page<ModelFileDto> getModels(Pageable pageable) {
        Page<ModelFile> modelFiles = modelFileRepository.findAll(pageable);
        return getModelFileDtosFrom(modelFiles);
    }

    @Override
    public List<ModelFileDto> getModelsByUserId(
            Long userId
    ) {
        Optional<PortalUser> foundUser = portalUsersRepository.findById(userId);
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();

            List<ModelFile> userModelFiles = portalUser.getModels();

            return getModelFileDtosFrom(userModelFiles);
        }
        throw new IllegalArgumentException("No such user exist");
    }

}
