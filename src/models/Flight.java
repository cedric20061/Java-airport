package src.models;

import java.util.*;

public class Flight {
    private int id;
    private String flightCode;
    private String departureLocation;
    private String destination;
    private String departureTime;
    private int durationInMinutes;
    private Map<String, SeatClass> seatClasses; // Stocke les classes et leur disposition
    private List<Reservation> reservations; // Liste des réservations

    public Flight(int id, String flightCode, String departureLocation, String destination,
                  String departureTime, int durationInMinutes, Map<String, SeatClass> seatClasses) {
        this.id = id;
        this.flightCode = flightCode;
        this.departureLocation = departureLocation;
        this.destination = destination;
        this.departureTime = departureTime;
        this.durationInMinutes = durationInMinutes;
        this.seatClasses = seatClasses;
        this.reservations = new ArrayList<>();
    }

    public int getTotalSeats() {
        return seatClasses.values().stream().mapToInt(SeatClass::getTotalSeats).sum();
    }

    public Map<String, Integer> getAvailableSeatsByClass() {
        Map<String, Integer> availableSeats = new HashMap<>();
        for (String className : seatClasses.keySet()) {
            availableSeats.put(className, seatClasses.get(className).getAvailableSeatsCount());
        }
        return availableSeats;
    }

    public boolean reserveSeat(int passengerId, String seatNumber) {
        for (SeatClass seatClass : seatClasses.values()) {
            if (seatClass.reserveSeat(seatNumber)) {
                reservations.add(new Reservation(reservations.size() + 1, this.id, passengerId, seatNumber));
                return true;
            }
        }
        return false;
    }

    public List<String> getOccupiedSeats() {
        List<String> occupiedSeats = new ArrayList<>();
        for (Reservation res : reservations) {
            occupiedSeats.add(res.getSeatNumber());
        }
        return occupiedSeats;
    }
    public int getId() {
        return id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return departureTime;
    }

    // public int getAvailableSeats() {
    //     return availableSeats;
    // }
}
