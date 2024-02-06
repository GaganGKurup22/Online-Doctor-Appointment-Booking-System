
package Frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HospitalInterface extends JFrame {

    private JComboBox<String> doctorComboBox;
    private JTable appointmentTable;
    private JButton refreshButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/doctorbooking";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1234";

    public HospitalInterface() {
        // Set up the main frame
        setTitle("Hospital Appointment Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Use a JPanel with BorderLayout for better organization
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Add a JLabel for the background image
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:/Users/HP/Desktop/hospital-logo-and-symbols-template-icons-vector.jpg"));
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);

        // Initialize Swing components
        JLabel selectDoctorLabel = new JLabel("Select Doctor:");
        doctorComboBox = new JComboBox<>(new String[]{"Dr. Smith", "Dr. Johnson", "Dr. Williams", "Dr. Jones", "Dr. Brown", "Dr. Davis", "Dr. Miller"});
        appointmentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        refreshButton = new JButton("Refresh");

        // Set layout and add components
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBackground(new Color(41, 128, 185)); // Dark blue color
        selectDoctorLabel.setForeground(Color.WHITE);
        topPanel.add(selectDoctorLabel);
        doctorComboBox.setBackground(new Color(236, 240, 241)); // Light gray color
        topPanel.add(doctorComboBox);
        refreshButton.setBackground(new Color(46, 204, 113)); // Green color (different shade)
        refreshButton.setForeground(Color.WHITE);
        topPanel.add(refreshButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        appointmentTable.setBackground(new Color(236, 240, 241)); // Light gray color
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listener to refresh appointments when a doctor is selected
        doctorComboBox.addActionListener(e -> fetchDataFromDatabase((String) doctorComboBox.getSelectedItem()));

        // Add action listener to the refresh button
        refreshButton.addActionListener(e -> fetchDataFromDatabase((String) doctorComboBox.getSelectedItem()));

        // Fetch and display data from the database for the initial doctor
        fetchDataFromDatabase((String) doctorComboBox.getSelectedItem());

        // Set Dr. Smith as initially selected in the center
        doctorComboBox.setSelectedItem("Dr. Smith");
    }

    private void fetchDataFromDatabase(String selectedDoctor) {
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String tableName = getTableName(selectedDoctor);

                String query = "SELECT * FROM doctorbooking." + tableName;
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    DefaultTableModel tableModel = new DefaultTableModel();
                    for (int i = 1; i <= columnCount; i++) {
                        tableModel.addColumn(metaData.getColumnName(i));
                    }

                    while (resultSet.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = resultSet.getObject(i);
                        }
                        tableModel.addRow(rowData);
                    }

                    appointmentTable.setModel(tableModel);

                    System.out.println("Data fetched successfully");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getTableName(String selectedDoctor) {
        switch (selectedDoctor) {
            case "Dr. Smith":
                return "drsmith";
            case "Dr. Johnson":
                return "drjohnson";
            case "Dr. Williams":
                return "drwilliams";
            case "Dr. Jones":
                return "drjones";
            case "Dr. Brown":
                return "drbrown";
            case "Dr. Davis":
                return "drdavis";
            case "Dr. Miller":
                return "drmiller";
            default:
                throw new IllegalArgumentException("Invalid doctor name: " + selectedDoctor);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}



class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setResizable(false);

        // Set the background color
        getContentPane().setBackground(new Color(44, 62, 80)); // Dark blue color

        // Initialize Swing components
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));

        // Set layout and add components using GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Password label and text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        // Login button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Add action listener to the login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if ("username".equals(username) && "password".equals(password)) {
                openHospitalInterface();
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void openHospitalInterface() {
        SwingUtilities.invokeLater(() -> {
            new HospitalInterface().setVisible(true);
            dispose();
        });
    }
}
