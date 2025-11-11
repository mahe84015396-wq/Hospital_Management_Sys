import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalManagement extends JFrame {
    JTextField nameField, ageField, contactField;
    JComboBox<String> genderBox;
    JTextArea outputArea;

    public HospitalManagement() {
        setTitle("Hospital Management System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Input fields
        add(new JLabel("Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Age:"));
        ageField = new JTextField(5);
        add(ageField);

        add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Male", "Female"});
        add(genderBox);

        add(new JLabel("Contact:"));
        contactField = new JTextField(15);
        add(contactField);

        // Buttons
        JButton addBtn = new JButton("Add Patient");
        JButton viewBtn = new JButton("View Patients");
        add(addBtn);
        add(viewBtn);

        // Output area
        outputArea = new JTextArea(10, 30);
        add(new JScrollPane(outputArea));

        // Add functionality
        addBtn.addActionListener(e -> addPatient());
        viewBtn.addActionListener(e -> viewPatients());
    }

    // Method to add patient to DB
    void addPatient() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = (String) genderBox.getSelectedItem();
        String contact = contactField.getText();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO patients (name, age, gender, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, contact);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Patient added successfully!");
            nameField.setText("");
            ageField.setText("");
            contactField.setText("");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding patient!");
        }
    }

    // Method to view patients
    void viewPatients() {
        outputArea.setText("");
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients");
            while (rs.next()) {
                outputArea.append(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("age") + " | " +
                        rs.getString("gender") + " | " +
                        rs.getString("contact") + "\n"
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HospitalManagement().setVisible(true);
    }
}
