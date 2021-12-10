package ru.itis.threedportalserver.contollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping(value = "/api/auth/login")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("kyliti");
    }
}
