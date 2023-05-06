package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.helpers.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.DefaultRoles;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.RoleDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.helpers.UserAccountHelper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAccountHelperImpl implements UserAccountHelper {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

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

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        boolean isSecure = false;
        String contextPath = null;
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            isSecure = request.isSecure();
            contextPath = request.getContextPath();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
        if (response != null) {
            Cookie cookie = new Cookie("JSESSIONID", null);
            String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            cookie.setSecure(isSecure);
            response.addCookie(cookie);
        }
    }

    @Override
    public void addDefaultRoles(UserEntity entity) {
        entity.setRoles(List.of(roleDAO.findByName(DefaultRoles.USER_ROLE.getValue()).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(
                                ExceptionMessages.ROLE_BY_NAME_NOT_FOUND.getValue(),
                                DefaultRoles.USER_ROLE.getValue()
                        )
                )
        )));
    }
}
