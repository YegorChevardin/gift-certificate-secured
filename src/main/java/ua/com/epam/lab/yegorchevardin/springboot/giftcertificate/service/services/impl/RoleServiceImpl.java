package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.RoleDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.RoleService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO;
    private final DomainObjectsConvertor<RoleEntity, Role>
            roleEntityRoleDomainObjectsConvertor;

    @Override
    public Role findById(long id) {
        return roleEntityRoleDomainObjectsConvertor.convertEntityToDTO(
                findByIdIfExists(id)
        );
    }

    @Override
    public List<Role> findAll(int page, int size) {
        return roleDAO.findAll(PageRequest.of(page, size)).stream().map(
                roleEntityRoleDomainObjectsConvertor::convertEntityToDTO
        ).toList();
    }

    @Override
    public Role insert(Role dto) {
        return roleEntityRoleDomainObjectsConvertor.convertEntityToDTO(
                roleDAO.insert(roleEntityRoleDomainObjectsConvertor.convertDtoToEntity(
                        dto
                )).orElseThrow(
                        () -> new DataExistException(
                                String.format(
                                        ExceptionMessages.ROLE_BY_NAME_EXISTS.getValue(),
                                        dto.getName()
                                )
                        )
                )
        );
    }

    @Override
    public void removeById(long id) {
        findByIdIfExists(id);
        roleDAO.removeById(id);
    }

    @Override
    public Role findByRoleValue(String role) {
        return roleEntityRoleDomainObjectsConvertor.convertEntityToDTO(
                findByNameIfExists(role)
        );
    }

    private RoleEntity findByNameIfExists(String name) {
        return roleDAO.findByName(name).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(
                                ExceptionMessages.ROLE_BY_NAME_NOT_FOUND.getValue(),
                                name
                        )
                )
        );
    }

    private RoleEntity findByIdIfExists(Long id) {
        return roleDAO.findById(id).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(
                                ExceptionMessages.ROLE_BY_ID_DOES_NOT_FOUND.getValue(),
                                id
                        )
                )
        );
    }
}
