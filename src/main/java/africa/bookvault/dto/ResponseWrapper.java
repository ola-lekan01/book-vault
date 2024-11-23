package africa.bookvault.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseWrapper<T> {

    private String code = "00";

    private String message = "Successful";

    private T data;

    private Map<String, Object> errors;

    public ResponseWrapper() {
    }

    public ResponseWrapper(T body) {
        setSuccessParams(body);
    }
    private void setSuccessParams(T body, String message) {
        setMessage(message);
        setData(body);
        setErrors(null);
    }

    private void setSuccessParams(T body) {
        setSuccessParams(body, message);
    }
}
