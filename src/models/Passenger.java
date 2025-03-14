package src.models;

import java.util.List;
import src.services.FlightService;
import src.services.ReservationService;

public class Passenger extends User {
    private List<Reservation> reservations;

    public Passenger(int id, String name, String email, String password) {
        super(id, name, email, password);
        this.reservations = ReservationService.getPassengerReservations(id);
    }

    @Override
    public String getRole() {
        return "Passenger";
    }

    // Réserver un vol avec choix de la classe et du siège
    public boolean bookFlight(int flightId, String seatClass, String seatNumber) {
        Flight flight = FlightService.getFlightById(flightId);
        if (flight == null) {
            System.out.println("Flight not found.");
            return false;
        }

        // Vérifier la disponibilité du siège dans la classe choisie
        if (!flight.getSeatClasses().containsKey(seatClass)) {
            System.out.println("Seat class not available.");
            return false;
        }

        SeatClass selectedClass = flight.getSeatClasses().get(seatClass);
        if (!selectedClass.reserveSeat(seatNumber)) {
            System.out.println("Seat already taken.");
            return false;
        }

        // Ajouter la réservation
        boolean success = ReservationService.addReservation(this.id, flightId, seatNumber);
        if (success) {
            reservations.add(new Reservation(reservations.size() + 1, flightId, this.id, seatNumber));
        }
        return success;
    }

    // Annuler une réservation
    public boolean cancelBooking(int flightId, String seatNumber) {
        boolean success = ReservationService.removeReservation(this.id, flightId, seatNumber);
        if (success) {
            reservations.removeIf(res -> res.getFlightId() == flightId && res.getSeatNumber().equals(seatNumber));
        }
        return success;
    }

    // Liste des vols réservés par le passager
    public List<Flight> getBookedFlights() {
        return FlightService.getFlightsByReservation(reservations);
    }
}
