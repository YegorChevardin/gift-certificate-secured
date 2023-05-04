package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final int TOKEN_VALIDITY = 86400;
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * This method allows to get the username from token
     *
     * @param token the string which contains hashed user data
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * This method allows to get the needed claim from token by getting the token and function
     * to get it.
     *
     * @param token the string which contains hashed user data
     * @param claimResolver function which helps to get the needed data
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    /**
     * This method allows to get all claims from the token.
     *
     * @param token the string which contains hashed user data
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Method for token validation
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * This method checks if the token is expired
     *
     * @param token the string which contains hashed user data
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    /**
     * This method allows to get the date when the token will expire
     *
     * @param token the string which contains hashed user data
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * This method generates the token by getting user details and hashing them using the
     * signature algorithm
     *
     * @param userDetails contains the details about the user
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
