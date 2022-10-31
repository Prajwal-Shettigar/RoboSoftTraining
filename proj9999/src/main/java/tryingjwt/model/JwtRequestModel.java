package tryingjwt.model;

import java.io.Serializable;

public class JwtRequestModel implements Serializable {

    private static final long serialVersionUID = 2636936156391265891L;
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
