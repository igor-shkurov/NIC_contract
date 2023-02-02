package nic.task.accountingsystem.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import nic.task.accountingsystem.entities.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static nic.task.accountingsystem.security.jwt.AlgorithmBuilder.algorithmInstance;
import static nic.task.accountingsystem.security.jwt.JWTUtils.JWT_ACCESS_DURATION;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/token")
public class TokenController {
    private final CustomUserDetailsService userService;

    @Autowired
    public TokenController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = algorithmInstance;
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDetails user = userService.loadUserByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_DURATION))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                JWTUtils.writeTokensToJSON(response, access_token, refresh_token);
            }
            catch (Exception exception) {
                JWTUtils.writeExceptionToJSON(response, exception);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
