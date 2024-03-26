package GDNTiendas.GDNTIENDAS.service;

import GDNTiendas.GDNTIENDAS.config.models.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

public interface IJwtUtilityService {
String generateToken(Long id, String email, Role role) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException;
JWTClaimsSet parseToken(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException;
}
