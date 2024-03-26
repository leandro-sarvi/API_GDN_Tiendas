package GDNTiendas.GDNTIENDAS.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
/*
Anotamos la clase con @ControllerAdvice,
lo que indica a Spring que esta clase manejará excepciones para todos los controladores en la aplicación.
*/
public class CustomerExceptionHandler extends ResponseEntityExceptionHandler {
   /*
    Usamos @ExceptionHandler para indicar que el método handleNotFoundExceptions manejará excepciones de tipo NotFoundExceptions.
    */
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundExceptions(ResourceNotFoundException ex, WebRequest request){
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(), ex.getHttpStatus(),request);
    }
    @ExceptionHandler(value = {TiendaBadRequestException.class})
    protected ResponseEntity<Object> handleBadRequestExceptions(TiendaBadRequestException ex, WebRequest request){
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(), ex.getStatus(),request);
    }
}
