package ru.itis.threedportalserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.threedportalserver.models.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {

    public Long id;
    public String email;

    public static ProfileDto from(Profile profile) {
        return ProfileDto.builder()
                .email(profile.getUser().getEmail())
                .id(profile.getId())
                .build();
    }

}
