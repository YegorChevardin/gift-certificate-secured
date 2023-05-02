package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import org.springframework.stereotype.Service;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.RoleService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public Role findById(long id) {
        return null;
    }

    @Override
    public List<Role> findAll(int page, int size) {
        return null;
    }

    @Override
    public Role insert(Role dto) {
        return null;
    }

    @Override
    public void removeById(long id) {

    }

    @Override
    public Role findByRoleValue(String role) {
        return null;
    }
}
