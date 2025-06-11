package models;

import java.io.Serializable;

public class Flight implements Serializable {
    private String flightId;
    private String destination;
    private String dateDepart; // format YYYY-MM-DD
    private int seatsNumber;

    public Flight(String numeroVol, String destination, String dateDepart, int seatsNumber) {
        this.flightId = numeroVol;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.seatsNumber = seatsNumber;
    }

    // Getters et setters
    public String getFlightId() {
        return this.flightId;
    }
    public int getSeatsNumber() {
        return this.seatsNumber;
    }

    public int setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
        return this.seatsNumber;
    }
    @Override
    public String toString() {
        return flightId + ";" + destination + ";" + dateDepart + ";" + seatsNumber;
    }

    public static Flight fromString(String line) {
        String[] parts = line.split(";");
        return new Flight(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
    }
}
