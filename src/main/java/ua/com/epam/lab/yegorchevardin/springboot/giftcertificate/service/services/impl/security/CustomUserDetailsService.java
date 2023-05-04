package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.CustomUserDetails;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom user details service for spring security
 * @author yegorchevardin
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userDAO.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format(
                                ExceptionMessages.USER_BY_USERNAME_NOT_FOUND.getValue(),
                                username
                        )
                )
        );
        return new CustomUserDetails(username, entity.getPassword(), getAuthority(entity));
    }

    /**
     * This method matcher the role and set the authority to it
     * @param user contains the user's data
     */
    private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        });
        return authorities;
    }

}
