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
public class UserDto {

    private String email;
    private Long id;

    public static UserDto from(PortalUser portalUser) {
        return UserDto.builder()
                .id(portalUser.getId())
                .email(portalUser.getEmail())
                .build();
    }
}
