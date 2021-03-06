package dartsgame.buiseness;

import dartsgame.models.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidationErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = getMessages(ex.getBindingResult());
        return new ResponseEntity<>(new ResultDTO(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
    public ResponseEntity<ResultDTO> handleMethodeTypeMismatchException(Exception ex) {
        return new ResponseEntity<>(new ResultDTO.WrongRequest(), HttpStatus.BAD_REQUEST);
    }

    private String getMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors().get(0).getDefaultMessage();
    }
}
