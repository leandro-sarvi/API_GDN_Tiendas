package GDNTiendas.GDNTIENDAS.service.impl;

import GDNTiendas.GDNTIENDAS.config.models.Role;
import GDNTiendas.GDNTIENDAS.service.IJwtUtilityService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtilityService implements IJwtUtilityService {
    @Value("classpath:jwtKeys/private_key.pem")
    private Resource privateKeyResource;
    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;
    @Override
    public String generateToken(Long id, String email, Role role) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        PrivateKey privateKey = loadPrivateKey(privateKeyResource);
        Date now = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(id.toString())
                .claim("email", email)
                .claim("role",role)
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + 1800000))
                .build();
        JWSSigner signer = new RSASSASigner(privateKey);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(new JOSEObjectType("JWT")).build();
        SignedJWT signedJWT = new SignedJWT(header,claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
    @Override
    public JWTClaimsSet parseToken(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        PublicKey publicKey = loadPublicKey(publicKeyResource);
        SignedJWT signedJWT = SignedJWT.parse(jwt);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        if(!signedJWT.verify(verifier)){
            throw  new JOSEException("Invalid signatured");
        }
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        if(claimsSet.getExpirationTime().before(new Date())){
            throw new JOSEException("Expired Token");
        }
        return claimsSet;
    }

    private PrivateKey loadPrivateKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        //Este código lee todos los bytes del archivo especificado por ruta_del_archivo.txt utilizando Files.readAllBytes(Path path).
        // La ruta del archivo se crea utilizando Paths.get(String path)
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s","");
        //Este código decodifica la cadena Base64 almacenada en privateKeyPEM utilizando Base64.getDecoder().decode(String)
        // y almacena el resultado en un array de bytes llamado decodeKey
        byte[] decodeKey = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return  keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
    }
    private PublicKey loadPublicKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replace("\\s","");
        byte[] decodeKey = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
    }
}
