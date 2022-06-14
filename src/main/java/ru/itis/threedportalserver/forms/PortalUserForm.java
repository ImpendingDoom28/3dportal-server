package ru.itis.threedportalserver.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.threedportalserver.models.PortalUserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortalUserForm {

    private Long id;
    private PortalUserRole role;
}
