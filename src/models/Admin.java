package src.models;

import src.services.FlightService;

public class Admin extends User {

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    // Ajouter un vol
    public boolean addFlight(String flightCode, String destination, String date, int seats) {
        return FlightService.addFlight(flightCode, destination, date, seats);
    }

    // Supprimer un vol
    public boolean removeFlight(int flightId) {
        return FlightService.removeFlight(flightId);
    }

    // Modifier un vol
    public boolean updateFlight(int flightId, String flightCode, String destination, String date, int seats){
        return FlightService.updateFlight(flightId, flightCode, destination, date, seats);
    }
}
