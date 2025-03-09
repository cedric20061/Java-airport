package src.models;

import java.time.LocalDate;

public class Flight {
    private int id;
    private String flightCode;
    private String destination;
    private LocalDate date;
    private int availableSeats;

    public Flight(int id, String flightCode, String destination, LocalDate date, int availableSeats) {
        this.id = id;
        this.flightCode = flightCode;
        this.destination = destination;
        this.date = date;
        this.availableSeats = availableSeats;
    }

    public boolean reserveSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public void cancelReservation() {
        availableSeats++;
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

    public LocalDate getDate() {
        return date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
