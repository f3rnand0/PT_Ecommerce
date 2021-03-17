package perfiltic.ecommerce.api.exception;

import org.springframework.stereotype.Component;

@Component
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
