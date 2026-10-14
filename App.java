import javax.swing.*;
import java.awt.*;
import java.io.*;

public class App {

    static JTextField trainField;
    static JTextField dayField;
    static JTextArea result;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Smart Train Crowd Prediction");
        frame.setSize(750, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "SMART TRAIN CROWD PREDICTION",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        frame.add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(
                new GridLayout(6, 2, 10, 10)
        );

        inputPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        );

        inputPanel.add(new JLabel("Train Number:"));

        trainField = new JTextField();
        inputPanel.add(trainField);

        inputPanel.add(new JLabel("Day:"));

        dayField = new JTextField();
        inputPanel.add(dayField);

        JButton searchButton =
                new JButton("SEARCH TRAIN");

        JButton allButton =
                new JButton("SHOW ALL TRAINS");

        inputPanel.add(searchButton);
        inputPanel.add(allButton);

        JButton crowdButton =
                new JButton("CROWD PREDICTION");

        JButton clearButton =
                new JButton("CLEAR");

        inputPanel.add(crowdButton);
        inputPanel.add(clearButton);

        JButton dayButton =
                new JButton("DAY WISE ANALYSIS");

        JButton summaryButton =
                new JButton("PROJECT SUMMARY");

        inputPanel.add(dayButton);
        inputPanel.add(summaryButton);

        frame.add(inputPanel, BorderLayout.CENTER);

        result = new JTextArea();

        result.setFont(
                new Font("Consolas", Font.PLAIN, 15)
        );

        result.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(result);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Result")
        );

        frame.add(
                scrollPane,
                BorderLayout.SOUTH
        );

        searchButton.addActionListener(
                e -> searchTrain()
        );

        allButton.addActionListener(
                e -> showAllTrains()
        );

        crowdButton.addActionListener(
                e -> crowdPrediction()
        );

        dayButton.addActionListener(
                e -> dayWiseAnalysis()
        );

        summaryButton.addActionListener(
                e -> projectSummary()
        );

        clearButton.addActionListener(e -> {

            trainField.setText("");
            dayField.setText("");
            result.setText("");

        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static void searchTrain() {

        String trainNumber =
                trainField.getText().trim();

        String day =
                dayField.getText().trim();

        if (trainNumber.isEmpty()
                || day.isEmpty()) {

            result.setText(
                    "Please enter Train Number and Day."
            );

            return;
        }

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    "data/train_data.csv"
                            )
                    );

            String line;

            br.readLine();

            boolean found = false;

            while ((line = br.readLine()) != null) {

                String[] data =
                        line.split(",");

                if (data[0].equals(trainNumber)
                        && data[4].equalsIgnoreCase(day)) {

                    int totalSeats =
                            Integer.parseInt(data[7]);

                    int bookedSeats =
                            Integer.parseInt(data[8]);

                    int availableSeats =
                            totalSeats - bookedSeats;

                    double percentage =
                            bookedSeats * 100.0
                                    / totalSeats;

                    String crowd;
                    String demand;

                    if (percentage >= 80) {

                        crowd = "HIGH CROWD";
                        demand = "HIGH DEMAND";

                    } else if (percentage >= 50) {

                        crowd = "MEDIUM CROWD";
                        demand = "MEDIUM DEMAND";

                    } else {

                        crowd = "LOW CROWD";
                        demand = "LOW DEMAND";
                    }

                    result.setText(

                            "===== TRAIN DETAILS =====\n\n" +

                            "Train        : "
                                    + data[1] + "\n" +

                            "Train Number : "
                                    + data[0] + "\n" +

                            "Source       : "
                                    + data[2] + "\n" +

                            "Destination  : "
                                    + data[3] + "\n" +

                            "Day          : "
                                    + data[4] + "\n" +

                            "Month        : "
                                    + data[5] + "\n" +

                            "Holiday      : "
                                    + data[6] + "\n\n" +

                            "Total Seats  : "
                                    + totalSeats + "\n" +

                            "Booked Seats : "
                                    + bookedSeats + "\n" +

                            "Available    : "
                                    + availableSeats + "\n\n" +

                            String.format(
                                    "Crowd        : %.2f%% - %s\n",
                                    percentage,
                                    crowd
                            ) +

                            "Demand       : "
                                    + demand
                    );

                    found = true;

                    break;
                }
            }

            br.close();

            if (!found) {

                result.setText(
                        "Train data not found."
                );
            }

        } catch (Exception e) {

            result.setText(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void showAllTrains() {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    "data/train_data.csv"
                            )
                    );

            String line;

            br.readLine();

            StringBuilder output =
                    new StringBuilder(
                            "===== ALL TRAIN DATA =====\n\n"
                    );

            while ((line = br.readLine()) != null) {

                output.append(line)
                        .append("\n");
            }

            br.close();

            result.setText(
                    output.toString()
            );

        } catch (Exception e) {

            result.setText(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void crowdPrediction() {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    "data/train_data.csv"
                            )
                    );

            String line;

            br.readLine();

            StringBuilder output =
                    new StringBuilder(
                            "===== CROWD PREDICTION =====\n\n"
                    );

            while ((line = br.readLine()) != null) {

                String[] data =
                        line.split(",");

                int totalSeats =
                        Integer.parseInt(data[7]);

                int bookedSeats =
                        Integer.parseInt(data[8]);

                double percentage =
                        bookedSeats * 100.0
                                / totalSeats;

                String crowd;
                String demand;

                if (percentage >= 80) {

                    crowd = "HIGH CROWD";
                    demand = "HIGH DEMAND";

                } else if (percentage >= 50) {

                    crowd = "MEDIUM CROWD";
                    demand = "MEDIUM DEMAND";

                } else {

                    crowd = "LOW CROWD";
                    demand = "LOW DEMAND";
                }

                output.append(
                        String.format(
                                "%-20s %-10s %.2f%%  %-15s %-15s%n",
                                data[1],
                                data[4],
                                percentage,
                                crowd,
                                demand
                        )
                );
            }

            br.close();

            result.setText(
                    output.toString()
            );

        } catch (Exception e) {

            result.setText(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void dayWiseAnalysis() {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    "data/train_data.csv"
                            )
                    );

            String line;

            br.readLine();

            StringBuilder output =
                    new StringBuilder(
                            "===== DAY WISE ANALYSIS =====\n\n"
                    );

            while ((line = br.readLine()) != null) {

                String[] data =
                        line.split(",");

                int totalSeats =
                        Integer.parseInt(data[7]);

                int bookedSeats =
                        Integer.parseInt(data[8]);

                double percentage =
                        bookedSeats * 100.0
                                / totalSeats;

                String crowd;

                if (percentage >= 80) {

                    crowd = "HIGH CROWD";

                } else if (percentage >= 50) {

                    crowd = "MEDIUM CROWD";

                } else {

                    crowd = "LOW CROWD";
                }

                output.append(
                        String.format(
                                "%-12s %-20s %.2f%%  %s%n",
                                data[4],
                                data[1],
                                percentage,
                                crowd
                        )
                );
            }

            br.close();

            result.setText(
                    output.toString()
            );

        } catch (Exception e) {

            result.setText(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void projectSummary() {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    "data/train_data.csv"
                            )
                    );

            String line;

            br.readLine();

            int records = 0;
            int totalSeats = 0;
            int bookedSeats = 0;

            int high = 0;
            int medium = 0;
            int low = 0;

            while ((line = br.readLine()) != null) {

                String[] data =
                        line.split(",");

                int seats =
                        Integer.parseInt(data[7]);

                int booked =
                        Integer.parseInt(data[8]);

                double percentage =
                        booked * 100.0 / seats;

                records++;

                totalSeats += seats;

                bookedSeats += booked;

                if (percentage >= 80) {

                    high++;

                } else if (percentage >= 50) {

                    medium++;

                } else {

                    low++;
                }
            }

            br.close();

            int availableSeats =
                    totalSeats - bookedSeats;

            result.setText(

                    "===== PROJECT SUMMARY =====\n\n" +

                    "Total Records      : "
                            + records + "\n" +

                    "Total Seats        : "
                            + totalSeats + "\n" +

                    "Total Booked Seats : "
                            + bookedSeats + "\n" +

                    "Available Seats    : "
                            + availableSeats + "\n\n" +

                    "High Crowd         : "
                            + high + "\n" +

                    "Medium Crowd       : "
                            + medium + "\n" +

                    "Low Crowd          : "
                            + low
            );

        } catch (Exception e) {

            result.setText(
                    "Error: " + e.getMessage()
            );
        }
    }
}