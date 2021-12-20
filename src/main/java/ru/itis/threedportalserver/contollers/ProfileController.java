package ru.itis.threedportalserver.contollers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/api/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

}
