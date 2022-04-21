package ru.itis.threedportalserver.services.interfaces;

import ru.itis.threedportalserver.forms.RegisterForm;

public interface RegisterService {

    boolean registerUser(RegisterForm registerForm);
}