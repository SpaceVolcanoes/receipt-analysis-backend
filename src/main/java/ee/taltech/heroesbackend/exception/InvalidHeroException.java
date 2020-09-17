package ee.taltech.heroesbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidHeroException extends RuntimeException {

    public InvalidHeroException() {
    }

    public InvalidHeroException(String message) {
        super(message);
    }
}
