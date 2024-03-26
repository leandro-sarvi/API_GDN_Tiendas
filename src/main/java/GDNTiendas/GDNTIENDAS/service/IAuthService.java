package GDNTiendas.GDNTIENDAS.service;

import GDNTiendas.GDNTIENDAS.persistence.dto.UserDto;
import GDNTiendas.GDNTIENDAS.service.models.LoginDto;
import GDNTiendas.GDNTIENDAS.service.models.ResponseDto;
import com.nimbusds.jose.JOSEException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public interface IAuthService {
    HashMap<String, String> login(LoginDto loginDto) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException;
    ResponseDto register(UserDto userDto);
}
