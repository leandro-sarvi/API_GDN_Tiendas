package GDNTiendas.GDNTIENDAS.mapper.Impl;

import GDNTiendas.GDNTIENDAS.mapper.IMapper;
import GDNTiendas.GDNTIENDAS.persistence.dto.UserDto;
import GDNTiendas.GDNTIENDAS.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserMapper implements IMapper<UserDto, User> {
    @Override
    public User map(UserDto in) {
        Date now = new Date();
        User user = new User();
        user.setUsername(in.getUsername());
        user.setFistName(in.getFistName());
        user.setLastName(in.getLastName());
        user.setEmail(in.getEmail());
        user.setPassword(in.getPassword());
        user.setRole(in.getRole());
        return user;
    }
}
