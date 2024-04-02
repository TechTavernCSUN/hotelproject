

public class ClientController {
    private DatabaseManager databaseManager;

    public ClientController(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void registerClient(String name, String email) {
        // Implement logic to register a new client
        Client client = new Client(name, email);
        databaseManager.addClient(client);
    }

    public void updateClientEmail(Client client, String newEmail) {
        // Implement logic to update client's email
        client.setEmail(newEmail);
        databaseManager.updateClient(client);
    }

    public void deleteClient(Client client) {
        // Implement logic to delete a client and associated reservations
        databaseManager.deleteClient(client);
    }
}
