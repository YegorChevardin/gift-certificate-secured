package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Customized spring userDetails class for app auth
 */
@Getter
@Setter
public class CustomUserDetails extends User {
    public CustomUserDetails(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
    }
}
