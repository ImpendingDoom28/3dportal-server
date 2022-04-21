package ru.itis.threedportalserver.services.interfaces;

import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.forms.LoginForm;

public interface LoginService {
    LoginDto login(LoginForm loginForm);
}
