package GDNTiendas.GDNTIENDAS.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceNotFoundException extends  RuntimeException{

        private String message;
        private HttpStatus httpStatus;

        public ResourceNotFoundException(String message){
            super(message);
            this.message=message;
            this.httpStatus=HttpStatus.NOT_FOUND;
        }
}
