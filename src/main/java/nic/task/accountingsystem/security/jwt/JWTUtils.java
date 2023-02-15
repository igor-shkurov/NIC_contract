package nic.task.accountingsystem.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class JWTUtils {
    public static final long JWT_ACCESS_DURATION = 10000000L * 60 * 1000;
    public static final int JWT_REFRESH_DURATION = 1000 * 60 * 1000;

    public static void writeTokensToJSON(HttpServletResponse response, String access_token, String refresh_token) throws IOException {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    public static void writeExceptionToJSON(HttpServletResponse response, Exception exception) throws IOException {
        String errorMessage;
        if (exception.getMessage() == null) {
            errorMessage = "Token is not valid (unknown error)";
        }
        else {
            errorMessage = exception.getMessage();
        }
        response.setHeader("Error", errorMessage);
        response.setStatus(UNAUTHORIZED.value());
        Map<String, String> error = new HashMap<>();
        exception.printStackTrace();
        error.put("error_message", errorMessage);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
