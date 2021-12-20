package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.forms.LoginForm;
import ru.itis.threedportalserver.models.MessageTypes;
import ru.itis.threedportalserver.services.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    public final LoginService loginService;

    @PostMapping(value = "/api/auth/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        try {
            LoginDto loginDto = loginService.login(loginForm);
            return ResponseEntity.ok(loginDto);
        } catch (Exception e) {
            MessageDto messageDto = MessageDto.builder()
                    .message(e.getMessage())
                    .type(MessageTypes.ERROR)
                    .build();
            return ResponseEntity.badRequest().body(messageDto);
        }
    }
}
