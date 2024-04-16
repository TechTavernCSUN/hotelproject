import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private Map<String, String> managerCredentials; // Email-password pairs for managers

    public AuthenticationManager() {
        managerCredentials = new HashMap<>();
        // Assume some initial managers
        managerCredentials.put("Mike@TechTavern.com", "manager");
        managerCredentials.put("Jacob@TechTavern.com", "manager");
        managerCredentials.put("Chris@TechTavern.com", "manager");
        managerCredentials.put("Joshua@TechTavern.com", "manager");
    }

    public boolean authenticateManager(String email, String password) {
        // Check if the provided email exists in the map of manager credentials
        if (managerCredentials.containsKey(email)) {
            // Check if the provided password matches the stored password for the email
            return managerCredentials.get(email).equals(password);
        }
        return false; // Email not found
    }
}
