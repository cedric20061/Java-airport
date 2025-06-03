package src.models;

import java.io.Serializable;

public class Reservation implements Serializable {
    private String nomClient;
    private String numeroVol;
    private int nombrePlacesReservees;

    public Reservation(String nomClient, String numeroVol, int nombrePlacesReservees) {
        this.nomClient = nomClient;
        this.numeroVol = numeroVol;
        this.nombrePlacesReservees = nombrePlacesReservees;
    }

    // Getters et setters

    @Override
    public String toString() {
        return nomClient + ";" + numeroVol + ";" + nombrePlacesReservees;
    }

    public static Reservation fromString(String line) {
        String[] parts = line.split(";");
        return new Reservation(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
}
