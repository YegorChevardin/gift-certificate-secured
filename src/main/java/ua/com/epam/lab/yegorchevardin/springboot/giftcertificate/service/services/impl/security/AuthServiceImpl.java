package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.*;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.AuthService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.UserService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.helpers.UserAccountHelper;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.JwtRequest;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.JwtResponse;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountHelper accountHelper;
    private final JwtUtil jwtUtil;
    private final DomainObjectsConvertor<UserEntity, User>
            userDomainObjectsConvertor;
    private final UserDAO userDAO;

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new IncorrectPasswordException();
        }

        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        return new JwtResponse(userService.findByUsername(username), newGeneratedToken);
    }

    @Override
    public User register(User user) {
        String password = user.getPassword();
        if (password == null || password.length() < 2 || password.length() > 50) {
            throw new DataNotValidException(
                    "Password cannot be null and must be greater " +
                            "than 2 and less than 50 characters"
            );
        }
        if (userDAO.findByUsername(user.getUsername()).isPresent()) {
            throw new DataExistException(
                    String.format(
                            ExceptionMessages.USER_BY_USERNAME_EXIST.getValue(),
                            user.getUsername())
            );
        }
        UserEntity entity = userDomainObjectsConvertor.convertDtoToEntity(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDomainObjectsConvertor.convertEntityToDTO(
                userDAO.insert(entity)
                        .orElseThrow(
                                () -> new DataNotFoundException(
                                        ExceptionMessages.USER_BY_ID_NOT_FOUND.getValue()
                                )
                        )
        );
    }

    @Override
    public User updateAccount(User user) {
        UserEntity entity = accountHelper.getLoggedUser();
        if (!entity.getId().equals(user.getId())) {
            throw new UpdateException(
                    "You can only update your account!"
            );
        }
        return userService.update(user);
    }

    @Override
    public User findCurrentAccount() {
        return userDomainObjectsConvertor.convertEntityToDTO(
                accountHelper.getLoggedUser()
        );
    }

    @Override
    public void deleteAccount(HttpServletRequest request, HttpServletResponse response) {
        userService.removeById(accountHelper.getLoggedUser().getId());
        accountHelper.logout(request, response);
    }
}
