package tryingjwt.model;

import java.io.Serializable;

public class JwtResponseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String token;
    public JwtResponseModel(String jwtToken) {
        token = jwtToken;
    }

    public String getToken() {
        return token;
    }
}
