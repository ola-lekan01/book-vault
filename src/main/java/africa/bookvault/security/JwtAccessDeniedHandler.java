package africa.bookvault.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request,
                       jakarta.servlet.http.HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        LocalDateTime datetime = LocalDateTime.now();
        String formattedDatetime = datetime
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("Timestamp", formattedDatetime);
        errorDetails.put("Success", false);
        errorDetails.put("Message", accessDeniedException.getMessage());
        String errorDetailsJson = new ObjectMapper().writeValueAsString(errorDetails);
        response.getWriter().write(errorDetailsJson);
    }
}
