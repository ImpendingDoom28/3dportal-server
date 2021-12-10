package ru.itis.threedportalserver.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.threedportalserver.dtos.MessageDto;
import ru.itis.threedportalserver.forms.RegisterForm;
import ru.itis.threedportalserver.models.MessageTypes;
import ru.itis.threedportalserver.services.RegisterService;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping(value = "/api/auth/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterForm registerform) {
        try {
            boolean isOk = registerService.registerUser(registerform);
            if (isOk) {
                MessageDto messageDto = MessageDto.builder()
                        .type(MessageTypes.SUCCESS)
                        .build();
                return ResponseEntity.ok(messageDto);
            } else {
                throw new Exception("Unknown error");
            }
        } catch (Exception e) {
            MessageDto messageDto = MessageDto.builder()
                    .message(e.getMessage())
                    .type(MessageTypes.ERROR)
                    .build();
            return ResponseEntity.badRequest().body(messageDto);
        }
    }
}
