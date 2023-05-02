package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.configurations.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.constants.AccessPoints;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.JwtUtil;

import java.io.IOException;

/**
 * This class contains the logic of the internal jwt request filter realization.
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";
    private static final int TOKEN_HEADER_BEGIN_INDEX = 7;
    private static final String UNABLE_GET_TOKEN_MESSAGE = "Unable to get jwt token";
    private static final String EXPIRED_TOKEN_MESSAGE = "Token has been expired";
    private static final String BEARER_PREFIX_ERROR_MESSAGE = "Jwt token doesn't start with Bearer";

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * This method filters the jwt token by getting the request from user
     *
     * @param request the request which is coming from user
     * @param response which should be given after filter
     * @param filterChain object which call doFilter method
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (AccessPoints.getAccessPoints().contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String jwtToken = null;
        String username = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(HEADER_PREFIX)) {
            jwtToken = requestTokenHeader.substring(TOKEN_HEADER_BEGIN_INDEX);

            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                sendErrorResponse(response, UNABLE_GET_TOKEN_MESSAGE);
                return;
            } catch (ExpiredJwtException e) {
                sendErrorResponse(response, EXPIRED_TOKEN_MESSAGE);
                return;
            }
        } else {
            sendErrorResponse(response, BEARER_PREFIX_ERROR_MESSAGE);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(prepareResponseJSON(errorMessage));
    }

    private String prepareResponseJSON(String errorMessage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(errorMessage);
    }
}
