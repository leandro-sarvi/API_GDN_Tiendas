package GDNTiendas.GDNTIENDAS.controller;

import GDNTiendas.GDNTIENDAS.persistence.dto.UserDto;
import GDNTiendas.GDNTIENDAS.service.IAuthService;
import GDNTiendas.GDNTIENDAS.service.models.LoginDto;
import GDNTiendas.GDNTIENDAS.service.models.ResponseDto;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private IAuthService authService;
    public AuthenticationController(IAuthService authService){
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<HashMap<String,String>> login(@RequestBody LoginDto loginDto) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        HashMap<String,String> login = this.authService.login(loginDto);
        if(login.containsKey("jwt")){
            return  ResponseEntity.status(HttpStatus.OK).body(login);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(login);
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody  UserDto userDto){
        ResponseDto responseDto = authService.register(userDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
