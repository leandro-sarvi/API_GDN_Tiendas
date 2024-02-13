package GDNTiendas.GDNTIENDAS.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
@EqualsAndHashCode(callSuper = true)
@Data
public class TiendaBadRequestException extends  RuntimeException{
    private String message;
    private HttpStatus status;
    public TiendaBadRequestException(String message){
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
