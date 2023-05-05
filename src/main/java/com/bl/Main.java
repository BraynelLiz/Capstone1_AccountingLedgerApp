package com.bl;

// Import all
import javax.sql.rowset.spi.TransactionalWriter;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileWriter;

public class Main{ // Main class
    static ArrayList<Transactions> transactions = new ArrayList<>(); // Static variable of type Arraylist of transaction
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadHomeScreen();
    }
    public static void loadHomeScreen() { // Create method for home screen
        String input;
        do { // display what's on home screen
            System.out.println("Please enter a command: ");
            System.out.println("\tD: Deposit");
            System.out.println("\tP: Make payment(debit)");
            System.out.println("\tL: Ledger");
            System.out.println("\tX: Exit");
            System.out.println("Command: ");
            input = scanner.nextLine();

            switch (input) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    displayLedger();
                    break;
                case "X":
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid input");
            }

        } while (!input.equalsIgnoreCase("X"));
    }

    public static void addDeposit() { // Create main method for deposit
        System.out.println("Please input the date of the deposit(YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.println("Please input the time of the deposit(HH-MM-SS): ");
        String time = scanner.nextLine();

        System.out.println("Please input a brief description of the deposit");
        String description = scanner.nextLine();

        System.out.println("Please input the vendor");
        String vendor = scanner.nextLine();

        System.out.println("Please input the amount");
        double amount = Double.parseDouble(scanner.nextLine());

        Transactions transactions = new Transactions(date, time, description, vendor, amount);
        System.out.println("Deposit added successfully. ");
        try {
            FileWriter FileWriter = new FileWriter("./src/main/java/com/bl/transactions.txt", true);
            FileWriter.write("\nDeposit " + date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
//                FileWriter
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }

    public static void makePayment() { // Create main method for make payment
        System.out.println("Please provide the date of your payment(yyyy-MM-DD): \n Answer: ");
        String date = scanner.nextLine();

        System.out.println("Please provide the time of you payment(hh:MM:ss): \n Answer: ");
        String time = scanner.nextLine();

        System.out.println("Please provide a description: \n Answer: ");
        String desc = scanner.nextLine();

        System.out.println("Please provide the vendor: \n Answer: ");
        String vendor = scanner.nextLine();

        System.out.println("Please provide the amount you are intending to pay: \n Answer: ");
        String amount = scanner.nextLine();
        try {
            FileWriter depositfiles = new FileWriter("./src/main/java/com/bl/transactions.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(depositfiles);

            bufferedWriter.write("\n" + date + "|" + time + "|" + desc + "|" + vendor + "|" + "-" + amount);

            System.out.println("Payment successful");
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayLedger() { // Create main method for display ledger

        String userInput;
        do {
            System.out.println("| Ledger |");
            System.out.println("\tA: All"); //display all entries
            System.out.println("\tD: Deposit"); //only display the entries that are depsoits into account
            System.out.println("\tP: Payments"); //only display the negative entires/paymnts
            System.out.println("\tR: Reports"); // screen allows user to run pre-defineed reports or run a custom search

            userInput = scanner.nextLine();

            switch (userInput) {
                case "A":
                    displayAll();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    runReports();
                    break;
                default:
                    System.out.println("Input command not found");
            }
        } while (!userInput.equalsIgnoreCase("3"));
    }

    public static void displayAll() { // Create main method for display all
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/main/java/com/bl/transactions.csv"));
            String file;
            file = bufferedReader.readLine();
            while (file != null) {
                System.out.println(bufferedReader.readLine());
            }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void displayDeposits() { // Create main method for display deposit
        try {
            FileReader transactionFile = new FileReader("./src/main/java/com/bl/transactions.txt");
            BufferedReader bufferedReader = new BufferedReader(transactionFile);

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] splitInput = input.split(Pattern.quote("|"));
                String dateInput = splitInput[0];
                String timeInput = splitInput[1];
                String descriptionInput = splitInput[2];
                String vendorInput = splitInput[3];
                double addDepositInput = Double.parseDouble(splitInput[4]);

                Transactions currentTransaction = new Transactions(dateInput, timeInput, descriptionInput, vendorInput, addDepositInput);
                System.out.printf(
                        currentTransaction.getDate(),
                        currentTransaction.getTime(),
                        currentTransaction.getDescription(),
                        currentTransaction.getVendor(),
                        currentTransaction.getDeposit()
                );
            }
            bufferedReader.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void displayPayments() { // Create main method for display payment
    }

    public static void runReports() { // Create main method for run reports
        String subInput;
        do {
            subInput = scanner.nextLine();
            System.out.println("\t1: Month to Date");
            System.out.println("\t2: Previous Month");
            System.out.println("\t3: Year to Date");
            System.out.println("\t4: Previous Year");
            System.out.println("\t5: Search for Vendor");
            System.out.println("\t0: Back");
            System.out.println("\tH: Home");

        } while (!subInput.equalsIgnoreCase("H"));
    }
}




