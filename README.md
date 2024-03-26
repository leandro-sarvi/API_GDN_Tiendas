** API_TIENDAS_GDN **
- Se emplea seguridad con Spring Security y JWT
- Emplea privateKey / publicKey con openssl
- Crea la carpeta KeyResource o jwtKey, dentro de /resources
- Comandos utilizados:
- openssl genrsa -out private_key.pem 2048
- openssl rsa -pubout -in private_key.pem -out public_key.pem
- No te olvides de configurar la DB :)
  Ya puedes iniciar el proyecto!!!
