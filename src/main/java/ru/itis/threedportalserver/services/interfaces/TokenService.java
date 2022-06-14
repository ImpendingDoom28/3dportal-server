package ru.itis.threedportalserver.services.interfaces;

import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.dtos.PortalUserDto;

public interface TokenService {

    LoginDto generateTokens(PortalUserDto userDto);
    Boolean verifyToken(String token);
}
