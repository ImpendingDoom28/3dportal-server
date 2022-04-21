package ru.itis.threedportalserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.dtos.ModelFileDto;
import ru.itis.threedportalserver.dtos.PortalUserDto;
import ru.itis.threedportalserver.models.ModelFile;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.repositories.BucketFileRepository;
import ru.itis.threedportalserver.repositories.ModelFileRepository;
import ru.itis.threedportalserver.repositories.UsersRepository;
import ru.itis.threedportalserver.services.interfaces.ModelsService;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ModelsServiceImpl implements ModelsService {

    private final ModelFileRepository modelFileRepository;
    private final UsersRepository usersRepository;
    private final BucketFileRepository bucketFileRepository;

    @Override
    public void saveModel(
            MultipartFile file,
            Long userId,
            String givenName,
            String lastModified
    ) {
        Optional<PortalUser> foundUser = usersRepository.findById(userId);
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();

            String generatedName = UUID.randomUUID().toString();
            String entityTag = bucketFileRepository.uploadFile(
                    file,
                    generatedName,
                    portalUser.getEmail()
            );

            String originalFileName = file.getOriginalFilename();
            String mimeType = originalFileName.substring(originalFileName.lastIndexOf("."));

            ModelFile modelFile = ModelFile.builder()
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
    public List<ModelFileDto> getModels() {
        List<S3Object> s3ObjectList = bucketFileRepository.getS3Files();
        return Collections.emptyList();
    }

    @Override
    public List<ModelFileDto> getModelsByUserId(
            Long userId
    ) {
        Optional<PortalUser> foundUser = usersRepository.findById(userId);
        if (foundUser.isPresent()) {
            PortalUser portalUser = foundUser.get();

            List<ModelFile> userModelFiles = portalUser.getModels();
            List<ModelFileDto> mappedModelFiles = new ArrayList<>();

            userModelFiles.forEach(userModelFile -> {
                String presignedModelUrl = bucketFileRepository.getPresignedUrlFromModelFile(userModelFile);

                mappedModelFiles.add(
                        ModelFileDto.builder()
                                .modelUrl(presignedModelUrl)
                                .uploadDate(userModelFile.getUploadDate())
                                .generatedName(userModelFile.getGeneratedName())
                                .givenName(userModelFile.getGivenName())
                                .mimeType(userModelFile.getMimeType())
                                .lastModified(userModelFile.getLastModified())
                                .user(PortalUserDto.from(userModelFile.getUserId()))
                                .build()
                );
            });

            return mappedModelFiles;
        } else {
            throw new IllegalArgumentException("No such user exist");
        }
    }

}
