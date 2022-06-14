package ru.itis.threedportalserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.threedportalserver.models.PortalUser;
import ru.itis.threedportalserver.models.PortalUserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortalUserDto {

    private Long id;
    private PortalUserRole role;
    private String email;

    public static PortalUserDto from(PortalUser portalUser) {
        return PortalUserDto.builder()
                .id(portalUser.getId())
                .role(portalUser.getUserRole())
                .email(portalUser.getEmail())
                .build();
    }
}
