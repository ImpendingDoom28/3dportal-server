package ru.itis.threedportalserver.services.interfaces;

import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.dtos.UserDto;

public interface TokenService {

    LoginDto generateTokens(UserDto userDto);
    Boolean verifyToken(String token);
}
