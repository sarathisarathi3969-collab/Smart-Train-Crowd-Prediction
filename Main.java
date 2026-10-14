import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== SMART TRAIN CROWD PREDICTION =====");
            System.out.println("1. Search Train");
            System.out.println("2. Show All Trains");
            System.out.println("3. Crowd Prediction");
            System.out.println("4. Seat Demand Analysis");
            System.out.println("5. Day Wise Analysis");
            System.out.println("6. Project Summary");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1)
                searchTrain(sc);
            else if (choice == 2)
                showAllTrains();
            else if (choice == 3)
                crowdPrediction();
            else if (choice == 4)
                seatDemandAnalysis();
            else if (choice == 5)
                dayWiseAnalysis();
            else if (choice == 6)
                projectSummary();
            else if (choice == 7) {
                System.out.println("Thank you!");
                break;
            }
            else
                System.out.println("Invalid choice!");
        }

        sc.close();
    }

    static void searchTrain(Scanner sc) {

        System.out.print("Enter Train Number: ");
        String trainNumber = sc.nextLine();

        System.out.print("Enter Day: ");
        String day = sc.nextLine();

        try {

            BufferedReader br = new BufferedReader(
                new FileReader("data/train_data.csv")
            );

            String line;
            br.readLine();

            boolean found = false;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data[0].equals(trainNumber) &&
                    data[4].equalsIgnoreCase(day)) {

                    int totalSeats = Integer.parseInt(data[7]);
                    int bookedSeats = Integer.parseInt(data[8]);
                    int availableSeats = totalSeats - bookedSeats;

                    double percentage =
                        bookedSeats * 100.0 / totalSeats;

                    String crowd;

                    if (percentage >= 80)
                        crowd = "High Crowd";
                    else if (percentage >= 50)
                        crowd = "Medium Crowd";
                    else
                        crowd = "Low Crowd";

                    System.out.println("\n===== TRAIN DETAILS =====");
                    System.out.println("Train: " + data[1]);
                    System.out.println("Source: " + data[2]);
                    System.out.println("Destination: " + data[3]);
                    System.out.println("Day: " + data[4]);
                    System.out.println("Total Seats: " + totalSeats);
                    System.out.println("Booked Seats: " + bookedSeats);
                    System.out.println("Available Seats: " + availableSeats);
                    System.out.printf(
                        "Crowd: %.2f%% - %s%n",
                        percentage, crowd
                    );

                    found = true;
                    break;
                }
            }

            br.close();

            if (!found)
                System.out.println("Train data not found.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void showAllTrains() {

        try {

            BufferedReader br = new BufferedReader(
                new FileReader("data/train_data.csv")
            );

            String line;
            br.readLine();

            System.out.println("\n===== ALL TRAIN DATA =====");

            while ((line = br.readLine()) != null)
                System.out.println(line);

            br.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void crowdPrediction() {

        try {

            BufferedReader br = new BufferedReader(
                new FileReader("data/train_data.csv")
            );

            String line;
            br.readLine();

            System.out.println("\n===== CROWD PREDICTION =====");

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int totalSeats = Integer.parseInt(data[7]);
                int bookedSeats = Integer.parseInt(data[8]);

                double percentage =
                    bookedSeats * 100.0 / totalSeats;

                String crowd;
                String demand;

                if (percentage >= 80) {
                    crowd = "High Crowd";
                    demand = "High Demand";
                }
                else if (percentage >= 50) {
                    crowd = "Medium Crowd";
                    demand = "Medium Demand";
                }
                else {
                    crowd = "Low Crowd";
                    demand = "Low Demand";
                }

                System.out.printf(
                    "%s | %s | %.2f%% | %s | %s%n",
                    data[1],
                    data[4],
                    percentage,
                    crowd,
                    demand
                );
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void seatDemandAnalysis() {

        try {

            BufferedReader br = new BufferedReader(
                new FileReader("data/train_data.csv")
            );

            String line;
            br.readLine();

            System.out.println("\n===== SEAT DEMAND ANALYSIS =====");

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int totalSeats = Integer.parseInt(data[7]);
                int bookedSeats = Integer.parseInt(data[8]);
                int availableSeats = totalSeats - bookedSeats;

                double percentage =
                    bookedSeats * 100.0 / totalSeats;

                String demand;

                if (percentage >= 80)
                    demand = "High Demand";
                else if (percentage >= 50)
                    demand = "Medium Demand";
                else
                    demand = "Low Demand";

                System.out.println("\nTrain: " + data[1]);
                System.out.println("Day: " + data[4]);
                System.out.println("Booked Seats: " + bookedSeats);
                System.out.println("Available Seats: " + availableSeats);
                System.out.printf("Booking Rate: %.2f%%%n", percentage);
                System.out.println("Seat Demand: " + demand);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void dayWiseAnalysis() {

        try {

            BufferedReader br = new BufferedReader(
                new FileReader("data/train_data.csv")
            );

            String line;
            br.readLine();

            Map<String, Integer> total = new LinkedHashMap<>();
            Map<String, Integer> high = new LinkedHashMap<>();
            Map<String, Integer> medium = new LinkedHashMap<>();
            Map<String, Integer> low = new LinkedHashMap<>();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String day = data[4];

                int totalSeats = Integer.parseInt(data[7]);
                int bookedSeats = Integer.parseInt(data[8]);

                double percentage =
                    bookedSeats * 100.0 / totalSeats;

                total.put(day, total.getOrDefault(day, 0) + 1);

                if (percentage >= 80)
                    high.put(day, high.getOrDefault(day, 0) + 1);
                else if (percentage >= 50)
                    medium.put(day, medium.getOrDefault(day, 0) + 1);
                else
                    low.put(day, low.getOrDefault(day, 0) + 1);
            }

            br.close();

            System.out.println("\n===== DAY WISE ANALYSIS =====");

            for (String day : total.keySet()) {

                System.out.println("\nDay: " + day);
                System.out.println("Total Records: " + total.get(day));
                System.out.println(
                    "High Crowd: " + high.getOrDefault(day, 0)
                );
                System.out.println(
                    "Medium Crowd: " + medium.getOrDefault(day, 0)
                );
                System.out.println(
                    "Low Crowd: " + low.getOrDefault(day, 0)
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void projectSummary() {

        try {

            BufferedReader br = new BufferedReader(
                new FileReader("data/train_data.csv")
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

                String[] data = line.split(",");

                int seats = Integer.parseInt(data[7]);
                int booked = Integer.parseInt(data[8]);

                double percentage =
                    booked * 100.0 / seats;

                records++;
                totalSeats += seats;
                bookedSeats += booked;

                if (percentage >= 80)
                    high++;
                else if (percentage >= 50)
                    medium++;
                else
                    low++;
            }

            br.close();

            int availableSeats = totalSeats - bookedSeats;

            System.out.println("\n===== PROJECT SUMMARY =====");
            System.out.println("Total Train Records: " + records);
            System.out.println("Total Seats: " + totalSeats);
            System.out.println("Booked Seats: " + bookedSeats);
            System.out.println("Available Seats: " + availableSeats);
            System.out.println("High Crowd Records: " + high);
            System.out.println("Medium Crowd Records: " + medium);
            System.out.println("Low Crowd Records: " + low);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}