package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.helpers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.helpers.UserAccountHelper;

@Component
@RequiredArgsConstructor
public class UserAccountHelperImpl implements UserAccountHelper {
    private final UserDAO userDAO;

    @Override
    public UserEntity getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userDAO.findByUsername(username).isEmpty()) {
            throw new DataNotFoundException(
                    String.format(
                            ExceptionMessages.USER_BY_USERNAME_NOT_FOUND.getValue(),
                            username
                    )
            );
        }
        return userDAO.findByUsername(username).get();
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
