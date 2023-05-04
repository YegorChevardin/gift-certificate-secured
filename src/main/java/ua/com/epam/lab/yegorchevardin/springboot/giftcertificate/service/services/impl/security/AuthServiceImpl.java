package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.IncorrectPasswordException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.UpdateException;
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
        return userService.insert(user);
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
}
