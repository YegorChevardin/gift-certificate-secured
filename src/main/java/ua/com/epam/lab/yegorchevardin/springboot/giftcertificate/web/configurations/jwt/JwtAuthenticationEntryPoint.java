package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.configurations.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class contains the method which starts the authorization and returning
 * the error if the user is unauthorized.
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * This method sends the message if the user is unauthorized
     *
     * @param request the request which is sending when the user tries to send the request
     * @param response the response which will possibly be given to the user
     * @param authException the exception which is throwing if the user is unauthorized
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}

