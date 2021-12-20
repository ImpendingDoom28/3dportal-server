package ru.itis.threedportalserver.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.dtos.LoginDto;
import ru.itis.threedportalserver.dtos.UserDto;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public LoginDto generateTokens(UserDto userDto) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            String accessToken = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userDto.getId().toString())
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
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getPayload() != null;
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

    }
}
