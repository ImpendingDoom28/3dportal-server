package ru.itis.threedportalserver.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.threedportalserver.constants.ExceptionStrings;
import ru.itis.threedportalserver.models.User;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = authentication.getName();

        DecodedJWT decodedJWT;

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            authentication.setAuthenticated(false);
            throw new AuthenticationCredentialsNotFoundException(ExceptionStrings.BAD_TOKEN);
        }

        String email = decodedJWT.getClaim("email").asString();
        Long id = decodedJWT.getClaim("sub").asLong();

        UserDetails userDetails =
                UserDetailsImpl.builder()
                        .user(
                                User.builder()
                                        .email(email)
                                        .id(id)
                                        .build()
                        )
                        .build();

        authentication.setAuthenticated(true);
        ((JwtAuthentication) authentication).setUserDetails(userDetails);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }


}