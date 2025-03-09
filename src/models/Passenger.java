package src.models;

import java.util.List;
import src.services.ReservationService;

public class Passenger extends User {
    private List<Integer> bookedFlights; // Liste des ID des vols réservés

    public Passenger(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "Passenger";
    }

    // Réserver un vol
    public boolean bookFlight(int flightId) {
        boolean success = ReservationService.addReservation(this.id, flightId);
        if (success) {
            bookedFlights.add(flightId);
        }
        return success;
    }

    // Annuler une réservation
    public boolean cancelBooking(int flightId) {
        boolean success = ReservationService.removeReservation(this.id, flightId);
        if (success) {
            bookedFlights.remove(Integer.valueOf(flightId));
        }
        return success;
    }

    public List<Integer> getBookedFlights() {
        return bookedFlights;
    }
}
