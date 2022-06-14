package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.threedportalserver.forms.PortalUserForm;
import ru.itis.threedportalserver.services.interfaces.PortalUserService;

@RestController
@RequiredArgsConstructor
public class PortalUserController {

    private final PortalUserService portalUserService;

    @PutMapping("/api/user")
    @CrossOrigin(origins = "*")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changeUser(
            @RequestBody PortalUserForm portalUserForm
    ) {
        return ResponseEntity.ok(
                portalUserService.changeUser(portalUserForm)
        );
    }

    @GetMapping("/api/user/{userId}/apiKey")
    @CrossOrigin(origins = "*")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getApiKey(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                portalUserService.getApiKey(userId)
        );
    }
}
