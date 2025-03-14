package src.models;

import java.util.*;

public class SeatClass {
    private String className;
    private int rows;
    private int seatsPerRow;
    private Set<String> occupiedSeats; // Contient les sièges déjà réservés

    public SeatClass(String className, int rows, int seatsPerRow) {
        this.className = className;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.occupiedSeats = new HashSet<>();
    }

    public int getTotalSeats() {
        return rows * seatsPerRow;
    }

    public int getAvailableSeatsCount() {
        return getTotalSeats() - occupiedSeats.size();
    }

    public boolean reserveSeat(String seatNumber) {
        if (!occupiedSeats.contains(seatNumber)) {
            occupiedSeats.add(seatNumber);
            return true;
        }
        return false;
    }

    public List<String> generateSeatNumbers() {
        List<String> seatNumbers = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (char seat = 'A'; seat < 'A' + seatsPerRow; seat++) {
                seatNumbers.add(row + String.valueOf(seat));
            }
        }
        return seatNumbers;
    }
}
