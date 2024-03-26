package GDNTiendas.GDNTIENDAS.service.impl;

import GDNTiendas.GDNTIENDAS.config.util.EmailValidate;
import GDNTiendas.GDNTIENDAS.mapper.Impl.UserMapper;
import GDNTiendas.GDNTIENDAS.persistence.dto.UserDto;
import GDNTiendas.GDNTIENDAS.persistence.entity.User;
import GDNTiendas.GDNTIENDAS.persistence.repository.UserRepository;
import GDNTiendas.GDNTIENDAS.service.IAuthService;
import GDNTiendas.GDNTIENDAS.service.models.LoginDto;
import GDNTiendas.GDNTIENDAS.service.models.ResponseDto;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final JwtUtilityService jwtUtilityService;
    private final EmailValidate emailValidate;
    private final UserMapper userMapper;
    public AuthService(UserRepository userRepository,JwtUtilityService jwtUtilityService, UserMapper userMapper, EmailValidate emailValidate){
        this.userRepository = userRepository;
        this.jwtUtilityService = jwtUtilityService;
        this.userMapper = userMapper;
        this.emailValidate = emailValidate;
    }
    @Override
    public HashMap<String, String> login(LoginDto loginDto) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
       HashMap<String,String> jwt = new HashMap<>();
       if(!emailValidate.validateEmail(loginDto.getEmail())){
           jwt.put("ERROR","Invalid email");
           return  jwt;
       }
       Optional<User> user = this.userRepository.findByEmail(loginDto.getEmail());
       if(user.isEmpty()){
           jwt.put("ERROR","User no registered!");
           return jwt;
       }
       if(verifyPassword(loginDto.getPassword(), user.get().getPassword())){
           jwt.put("jwt",this.jwtUtilityService.generateToken(user.get().getId(), loginDto.getEmail(), user.get().getRole()));
           return jwt;
       }
        jwt.put("ERROR","UNAUTHORIZED");
       return jwt;
    }

    @Override
    public ResponseDto register(UserDto userDto) {
        Date now = new Date();
        ResponseDto responseDto = new ResponseDto();
        User user = userMapper.map(userDto);
        if(emailValidate.validateEmail(userDto.getEmail())){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(userDto.getPassword()));
            user.setCreateAt(now);
            this.userRepository.save(user);
            responseDto.setMessage("User created!!!");
            return responseDto;
        }
        responseDto.setMessage("Invalid email!!!");
        return responseDto;
    }

    private boolean verifyPassword(String enteredPassword, String storePassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return  passwordEncoder.matches(enteredPassword,storePassword);
    }
}
