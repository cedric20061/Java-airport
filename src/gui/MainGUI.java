package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import models.Flight; // Ton modèle
import main.AirportHandler; // Gestionnaire de vols
import components.ButtonRenderer; // Rendu du bouton
import components.ButtonEditor; // Éditeur du bouton
public class MainGUI {
    public static void main(String[] args) {
        // Initialiser la fenêtre
        JFrame frame = new JFrame("Liste des vols");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // En-têtes des colonnes
        String[] columnNames = { "Vol ID", "Destination", "Départ", "Places dispo", "Actions" };

        // Obtenir les données des vols
        List<Flight> flights = AirportHandler.getFlights();
        Object[][] rowData = new Object[flights.size()][5];

        for (int i = 0; i < flights.size(); i++) {
            Flight f = flights.get(i);
            rowData[i][0] = f.getFlightId();
            rowData[i][1] = f.getDestination();
            rowData[i][2] = f.getDateDepart();
            rowData[i][3] = f.getSeatsNumber();
            rowData[i][4] = "Réserver"; // Texte pour le bouton (placeholder)
        }

        // Création du modèle
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Seulement la colonne Action est modifiable (boutons)
            }
        };

        JTable table = new JTable(model);

        // Ajouter un bouton dans la colonne "Actions"
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Ajouter la table à un scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Afficher
        frame.setVisible(true);
    }
}
