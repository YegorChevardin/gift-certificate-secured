package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotValidException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.UserService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.helpers.UserAccountHelper;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final DomainObjectsConvertor<UserEntity, User> userDomainObjectsConvertor;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountHelper accountHelper;

    @Override
    public User findById(long id) {
        return userDomainObjectsConvertor.convertEntityToDTO(
                findByIdIfExists(id)
        );
    }

    @Override
    public List<User> findAll(int page, int size) {
        return userDAO.findAll(PageRequest.of(page, size)).stream()
                .map(
                        userDomainObjectsConvertor::convertEntityToDTO
                ).toList();
    }

    @Override
    public User insert(User user) {
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
        accountHelper.addDefaultRoles(entity);
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
    public void removeById(long id) {
        findByIdIfExists(id);
        userDAO.removeById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDomainObjectsConvertor.convertEntityToDTO(
                userDAO.findByUsername(username).orElseThrow(
                        () -> new DataNotFoundException(
                                String.format(
                                        ExceptionMessages.USER_BY_USERNAME_NOT_FOUND.getValue(),
                                        username
                                )
                        )
                )
        );
    }

    private UserEntity findByIdIfExists(Long id) {
        return userDAO.findById(id).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(
                                ExceptionMessages.USER_BY_ID_NOT_FOUND.getValue(),
                                id
                        )
                )
        );
    }

    @Override
    public User update(User dto) {
        if (dto.getId() == null) {
            throw new DataNotFoundException(
                    "Cannot update object without the id"
            );
        } else if (userDAO.findById(dto.getId()).isEmpty()) {
            throw new DataNotFoundException(
                    String.format(
                            ExceptionMessages.USER_BY_ID_NOT_FOUND.getValue(),
                            dto.getId()
                    )
            );
        }
        if (userDAO.findByUsername(dto.getUsername()).isPresent()
        && !userDAO.findByUsername(dto.getUsername())
                .get().getId().equals(dto.getId())) {
            throw new DataExistException(
                    String.format(
                            ExceptionMessages.USER_BY_USERNAME_EXIST.getValue(),
                            dto.getUsername()
                    )
            );
        }
        UserEntity entity = userDomainObjectsConvertor.convertDtoToEntity(dto);
        entity.setId(dto.getId());
        return userDomainObjectsConvertor.convertEntityToDTO(
                userDAO.update(entity)
        );
    }
}
