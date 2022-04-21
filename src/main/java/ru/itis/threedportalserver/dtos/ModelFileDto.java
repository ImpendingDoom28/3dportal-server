package ru.itis.threedportalserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelFileDto {

    private String givenName;
    private String modelUrl;
    private String generatedName;
    private String lastModified;
    private String mimeType;
    private Long uploadDate;
    private PortalUserDto user;

}
