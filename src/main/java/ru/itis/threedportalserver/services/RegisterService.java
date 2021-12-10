package ru.itis.threedportalserver.services;

import ru.itis.threedportalserver.forms.RegisterForm;

public interface RegisterService {

    boolean registerUser(RegisterForm registerForm);
}