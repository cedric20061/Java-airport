package models;

import java.io.Serializable;

public class Reservation implements Serializable {
    private String clientName;
    private String flightId;
    private int seatsReserved;

    public Reservation(String clientName, String flightId, int seatsReserved) {
        this.clientName = clientName;
        this.flightId = flightId;
        this.seatsReserved = seatsReserved;
    }

    // Getters et setters
    public String getClientName() {
        return this.clientName;
    }
    public int getSeatsReserved() {
        return this.seatsReserved;
    }
    public String getFlightId() {
        return this.flightId;
    }
    @Override
    public String toString() {
        return clientName + ";" + flightId + ";" + seatsReserved;
    }

    public static Reservation fromString(String line) {
        String[] parts = line.split(";");
        return new Reservation(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
}
