import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private Map<String, String> userCredentials; // Email-password pairs

    public AuthenticationManager() {
        userCredentials = new HashMap<>();
        // Assume some initial users
        userCredentials.put("client1@example.com", "password1");
        userCredentials.put("client2@example.com", "password2");
        // Add manager credentials as well
        userCredentials.put("manager@example.com", "managerpassword");
    }

    public boolean authenticateClient(String email, String password) {
        // Check if the provided email exists in the map
        if (userCredentials.containsKey(email)) {
            // Check if the provided password matches the stored password for the email
            return userCredentials.get(email).equals(password);
        }
        return false; // Email not found
    }

    public boolean authenticateManager(String email, String password) {
        // Assume different credentials are required for managers
        return email.equals("manager@example.com") && password.equals("managerpassword");
    }
}
