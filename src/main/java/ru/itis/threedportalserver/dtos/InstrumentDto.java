package ru.itis.threedportalserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstrumentDto {

    private Long id;
    private String name;
    private String description;
    private String hostUrl;
    private PortalUserDto author;
}
