package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.threedportalserver.services.interfaces.ProfileService;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/api/profile/{userId}")
    @CrossOrigin(origins = "*")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(
                profileService.getUserProfileById(userId)
        );
    }

}
