package ru.itis.threedportalserver.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.dtos.UserDto;

@Service
public class TokenServiceImpl implements TokenService {

    private final Algorithm algorithm = Algorithm.HMAC256("secret");
    private final String issuer = "auth0";

    @Override
    public LoginDto generateTokens(UserDto userDto) {
        try {
            String accessToken = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("email", userDto.getEmail())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withIssuer(issuer)
                    .sign(algorithm);

            return LoginDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (JWTCreationException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    @Override
    public Boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getPayload() != null;
        } catch (JWTVerificationException exception){
            throw new IllegalArgumentException(exception.getMessage());
        }

    }
}
