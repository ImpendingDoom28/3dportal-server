package ru.itis.threedportalserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.threedportalserver.models.PortalUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortalUserDto {

    private Long id;
    private String email;

    public static PortalUserDto from(PortalUser portalUser) {
        return PortalUserDto.builder()
                .id(portalUser.getId())
                .email(portalUser.getEmail())
                .build();
    }
}
