
package Frontend;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 

public class MainFrame {
    private JLabel datetime;
    private JLabel title;
    private JComboBox<String> doctorComboBox;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField phone;
    private JDateChooser dateChooser;
    private JLabel availableDoctorsLabel;

    MainFrame() {
        JFrame frame = new JFrame("Hospital Booking");
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Create a panel for the title and date-time display at the top-center
        JPanel titleDatetimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleDatetimePanel.setBackground(new Color(34, 47, 62)); // Dark Blue Background

        // Set the title label
        title = new JLabel();
        title.setText("<html><div style='text-align: center; font-size: 24pt; font-weight: bold; color: #ecf0f1;'>"
                + "THOPPIL BAVA MULTI SPECIALITY HOSPITAL<br>"
                + "Kunnamkulam Road, Sheikh Nagar<br>"
                + "Thiruvananthapuram, Kerala.</div></html>");

        // Add the title label to the titleDatetimePanel
        titleDatetimePanel.add(title);

        // Set constraints for titleDatetimePanel
        titleDatetimePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add padding
        frame.add(titleDatetimePanel, BorderLayout.NORTH);

        // Create a panel for doctor selection, name, age, symptoms, and date in the center
        JPanel bookingPanel = createBookingPanel();
        frame.add(bookingPanel, BorderLayout.CENTER);

        // Create a panel for the date-time display at the right corner
        JPanel datetimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datetimePanel.setBackground(new Color(34, 47, 62)); // Dark Blue Background

        // Set the datetime label
        datetime = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, 16);
        datetime.setFont(font);
        datetime.setForeground(Color.WHITE);

        // Add the datetime label to the datetimePanel
        datetimePanel.add(datetime);

        // Add datetimePanel to the frame at the right
        frame.add(datetimePanel, BorderLayout.EAST);

        // Use a Timer to update the date and time every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime();
            }
        });
        timer.start();

        // Set the size of the frame based on the screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);
        frame.setSize(width, height);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // Date chooser
        JLabel dateLabel = new JLabel("Select Date:");
        dateLabel.setForeground(new Color(34, 47, 62)); // Dark Blue Text color
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(250, dateChooser.getPreferredSize().height)); // Increase width
        dateChooser.addPropertyChangeListener("date", evt -> {
            updateAvailableDoctors();
        });

        availableDoctorsLabel = new JLabel("Available Doctors: ");
        availableDoctorsLabel.setForeground(new Color(34, 47, 62)); // Dark Blue Text color

        panel.add(dateLabel, gbc);
        gbc.gridx++;
        panel.add(dateChooser, gbc);
        gbc.gridx++;
        panel.add(availableDoctorsLabel, gbc);

        // User information
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(34, 47, 62)); // Dark Blue Text color
        panel.add(nameLabel, gbc);

        gbc.gridx++;
        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createLineBorder(new Color(34, 47, 62))); // Dark Blue Text field border color
        gbc.gridwidth = 2;
        gbc.ipadx = 200; // Increase text field width
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(new Color(34, 47, 62)); // Dark Blue Text color
        panel.add(ageLabel, gbc);

        gbc.gridx++;
        ageField = new JTextField();
        ageField.setBorder(BorderFactory.createLineBorder(new Color(34, 47, 62))); // Dark Blue Text field border color
        panel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel phoneNumberLabel = new JLabel("Phone number:");
        phoneNumberLabel.setForeground(new Color(34, 47, 62)); // Dark Blue Text color
        panel.add(phoneNumberLabel, gbc);

        gbc.gridx++;
        phone = new JTextField();
        phone.setBorder(BorderFactory.createLineBorder(new Color(34, 47, 62))); // Dark Blue Text field border color
        gbc.gridwidth = 2;
        panel.add(phone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setForeground(new Color(34, 47, 62)); // Dark Blue Text color
        panel.add(doctorLabel, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        doctorComboBox = new JComboBox<>();
        doctorComboBox.setBorder(BorderFactory.createLineBorder(new Color(34, 47, 62))); // Dark Blue Combo box border color
        gbc.ipadx = 200; // Increase width for doctorComboBox
        panel.add(doctorComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton bookButton = new JButton("Book");
        bookButton.setForeground(Color.WHITE); // White Text color
        bookButton.setBackground(new Color(34, 47, 62)); // Dark Blue Button color
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String age = ageField.getText();
                String phoneNumber = phone.getText();
                Date selectedDate = dateChooser.getDate();

                if (isValidDate(selectedDate)) {
                    // Create an instance of backend
                    backend back = new backend(name, age, phoneNumber, selectedDate, (String) doctorComboBox.getSelectedItem());

                    // Call the adddata method for database insertion
                    back.adddata();

                    // Display details in a message box
                    String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(selectedDate);
                    String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                    String message = "Name: " + name + "\n" +
                            "Age: " + age + "\n" +
                            "Phone Number: " + phoneNumber + "\n" +
                            "Selected Doctor: " + selectedDoctor + "\n" +
                            "Selected Date: " + formattedDate + "\n" +
                            "Booking Confirmed";

                    JOptionPane.showMessageDialog(null, message, "Booking Details", JOptionPane.INFORMATION_MESSAGE);

                    // Clear text fields after booking
                    nameField.setText("");
                    ageField.setText("");
                    phone.setText("");
                    dateChooser.setDate(null);

                } else {
                    // Display a new frame indicating the date is invalid
                    JOptionPane.showMessageDialog(null, "Invalid date. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(bookButton, gbc);

        return panel;
    }

    private boolean isValidDate(Date date) {
        if (date == null) {
            return false;
        }

        // Perform additional validation if needed
        // For simplicity, this example considers all dates as valid
        return true;
    }

    private void updateDateTime() {
        Date now = new Date();

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(now);

        // Format the time in 12-hour format
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        String formattedTime = timeFormat.format(now);

        // Update the label with the formatted date and time with line breaks
        datetime.setText("<html>Date: " + formattedDate + "<br>Time: " + formattedTime + "</html>");
    }

    private void updateAvailableDoctors() {
        Date selectedDate = dateChooser.getDate();
        if (selectedDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            String[] availableDoctors = getAvailableDoctors(dayOfWeek);
            updateDoctorComboBox(availableDoctors);
            updateAvailableDoctorsLabel(availableDoctors);
        }
    }

    private String[] getAvailableDoctors(int dayOfWeek) {
        String[] allDoctors = {"Dr. Smith", "Dr. Johnson", "Dr. Williams", "Dr. Jones", "Dr. Brown", "Dr. Davis", "Dr. Miller"};
        String[] availableDoctors;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                availableDoctors = new String[]{"Dr. Smith", "Dr. Johnson"};
                break;
            case Calendar.THURSDAY:
            case Calendar.FRIDAY:
                availableDoctors = new String[]{"Dr. Johnson", "Dr. Jones", "Dr. Brown"};
                break;
            default:
                availableDoctors = allDoctors;
        }

        return availableDoctors;
    }

    private void updateDoctorComboBox(String[] availableDoctors) {
        doctorComboBox.removeAllItems();
        for (String doctor : availableDoctors) {
            doctorComboBox.addItem(doctor);
        }
    }

    private void updateAvailableDoctorsLabel(String[] availableDoctors) {
        StringBuilder doctorsText = new StringBuilder("Available Doctors: ");
        for (String doctor : availableDoctors) {
            doctorsText.append(doctor).append(", ");
        }
        // Remove the trailing comma and space
        doctorsText.setLength(doctorsText.length() - 2);

        availableDoctorsLabel.setText(doctorsText.toString());
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
