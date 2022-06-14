package ru.itis.threedportalserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.threedportalserver.models.ModelFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelFileDto {

    private String givenName;
    private String modelUrl;
    private String description;
    private String previewImageUrl;
    private String generatedName;
    private String lastModified;
    private String mimeType;
    private Long uploadDate;
    private PortalUserDto user;

}
