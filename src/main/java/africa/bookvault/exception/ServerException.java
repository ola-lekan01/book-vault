package africa.bookvault.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException{

    public ServerException(String message) {
        super(message);
    }
}
