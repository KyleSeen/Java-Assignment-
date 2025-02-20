package com.mycompany.javaass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {

    Scanner userInput = new Scanner(System.in);
    private static final String ADMIN_FILE = "admin_data.txt";

    public Admin() {}

    public String getAdminUsername() {
        return readAdminData(1);
    }

    public String getAdminPassword() {
        return readAdminData(2);
    }

    private String readAdminData(int index) {
        String data = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                String[] fields = line.split(",");
                if (fields.length > index) {
                    data = fields[index].trim();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file " + ADMIN_FILE + ": " + e.getMessage());
        }
        return data;
    }

    public void runAdminFunctions(boolean keepRunning) throws IOException {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=====================================");
            System.out.println("        Admin Control Panel         ");
            System.out.println("=====================================");
            System.out.println(" 1. Customer Management            ");
            System.out.println(" 2. Vendor Management              ");
            System.out.println(" 3. Runner Management              ");
            System.out.println(" 0. Logout                         ");
            System.out.println("=====================================");
            System.out.print("Choose an option: ");
            int selection = userInput.nextInt();
            userInput.nextLine();

            switch (selection) {
                case 1:
                    manageCustomers();
                    break;
                case 2:
                    manageVendors();
                    break;
                case 3:
                    manageRunners();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    sessionActive = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageCustomers() throws IOException {
        boolean continueCustomerOperations = true;
        while (continueCustomerOperations) {
            System.out.println("\n=====================================");
            System.out.println("        Customer Options Menu       ");
            System.out.println("=====================================");
            System.out.println(" 1. Add Customer                   ");
            System.out.println(" 2. Read Customer Data             ");
            System.out.println(" 3. Search Customer                ");
            System.out.println(" 4. Update Customer                ");
            System.out.println(" 5. Add Credit                     ");
            System.out.println(" 6. Delete Customer                ");
            System.out.println(" 7. Exit                           ");
            System.out.println("=====================================");
            System.out.print("Enter your choice: ");
            int customerChoice = userInput.nextInt();
            userInput.nextLine();

            switch (customerChoice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    System.out.println("\n============================");
                    System.out.println("|   Reading Customer Data  |");
                    System.out.println("============================");
                    CustomerDashboard.readCustomerData();
                    break;
                case 3:
                    System.out.println("\n============================");
                    System.out.println("|   Searching Customer     |");
                    System.out.println("============================");
                    System.out.print("Enter Customer ID to search: ");
                    int searchId = userInput.nextInt();
                    userInput.nextLine();
                    CustomerDashboard.searchCustomer(searchId);
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    addCredit();
                    break;
                case 6:
                    deleteCustomer();
                    break;
                case 7:
                    System.out.println("Exiting customer operations.");
                    continueCustomerOperations = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageVendors() throws IOException {
        boolean continueVendorOperations = true;
        while (continueVendorOperations) {
            System.out.println("\n========================================");
            System.out.println("           Vendor Operations           ");
            System.out.println("========================================");
            System.out.println(" 1. Add Vendor                        ");
            System.out.println(" 2. Read Vendor Data                  ");
            System.out.println(" 3. Search Vendor                     ");
            System.out.println(" 4. Update Vendor                     ");
            System.out.println(" 5. Delete Vendor                     ");
            System.out.println(" 6. Exit                              ");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            int vendorChoice = userInput.nextInt();
            userInput.nextLine();

            switch (vendorChoice) {
                case 1:
                    addVendor();
                    break;
                case 2:
                    System.out.println("\n============================");
                    System.out.println("|    Reading Vendor Data    |");
                    System.out.println("============================");
                    Vendor.readVendorData();
                    break;
                case 3:
                    System.out.println("\n============================");
                    System.out.println("|    Searching Vendor       |");
                    System.out.println("============================");
                    System.out.print("Enter Vendor ID to search: ");
                    int searchId = userInput.nextInt();
                    userInput.nextLine();
                    Vendor.searchVendor(searchId);
                    break;
                case 4:
                    updateVendor();
                    break;
                case 5:
                    System.out.println("\n=============================");
                    System.out.println("|    Deleting Vendor        |");
                    System.out.println("============================");
                    System.out.print("Enter Vendor ID to delete: ");
                    int deleteId = userInput.nextInt();
                    userInput.nextLine();
                    Vendor.deleteVendor(deleteId);
                    Vendor.deleteVendorFiles(deleteId);
                    break;
                case 6:
                    System.out.println("Exiting vendor operations.");
                    continueVendorOperations = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageRunners() throws IOException {
        boolean continueRunnerOperations = true;
        while (continueRunnerOperations) {
            System.out.println("\n=====================================");
            System.out.println("        Runner Operations Menu      ");
            System.out.println("=====================================");
            System.out.println(" 1. Add Runner                     ");
            System.out.println(" 2. Read Runner Data               ");
            System.out.println(" 3. Search Runner                  ");
            System.out.println(" 4. Update Runner                  ");
            System.out.println(" 5. Delete Runner                  ");
            System.out.println(" 6. Exit                           ");
            System.out.println("=====================================");
            System.out.print("Enter your choice: ");
            int runnerChoice = userInput.nextInt();
            userInput.nextLine();

            switch (runnerChoice) {
                case 1:
                    addRunner();
                    break;
                case 2:
                    System.out.println("\n============================");
                    System.out.println("|    Reading Runner Data     |");
                    System.out.println("============================");
                    Runner.readRunnerData();
                    break;
                case 3:
                    System.out.println("\n============================");
                    System.out.println("|     Searching Runner       |");
                    System.out.println("============================");
                    System.out.print("Enter Runner ID to search: ");
                    int searchId = userInput.nextInt();
                    userInput.nextLine();
                    Runner.searchRunner(searchId);
                    break;
                case 4:
                    updateRunner();
                    break;
                case 5:
                    System.out.println("\n============================");
                    System.out.println("|     Deleting Runner        |");
                    System.out.println("============================");
                    System.out.print("Enter Runner ID to delete: ");
                    int deleteId = userInput.nextInt();
                    userInput.nextLine();
                    Runner.deleteRunner(deleteId);
                    break;
                case 6:
                    System.out.println("Exiting runner operations.");
                    continueRunnerOperations = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addCustomer() throws IOException {
        System.out.println("\n============================");
        System.out.println("|   Adding New Customer    |");
        System.out.println("============================");
        System.out.println("Enter Customer Information");

        System.out.print("Username: ");
        String username = userInput.nextLine();

        System.out.print("Password: ");
        String password = userInput.nextLine();

        System.out.print("Credit: ");
        double credit = userInput.nextDouble();
        userInput.nextLine(); 

        if (!userExists(username, "customer_info.txt")) {
            CustomerDashboard newCustomer = new CustomerDashboard();
            newCustomer.writeCustomerData(username, password, credit);
        } else {
            System.out.println("Username already Exists.");
        }
    }

    private void updateCustomer() {
    System.out.println("\n============================");
    System.out.println("|   Updating Customer      |");
    System.out.println("============================");
    System.out.print("Enter Customer ID to update: ");
    int updateId = userInput.nextInt();
    userInput.nextLine(); // Consume newline

        if (CustomerDashboard.doesCustomerIdExist(updateId)) {
            System.out.print("Enter new Username: ");
            String newUsername = userInput.nextLine();
            System.out.print("Enter new Password: ");
            String newPassword = userInput.nextLine();

            if (userExists(newUsername, "customer_info.txt")) { // Fixed method call
                System.out.println("Username Already Exists.");
            } else {
                CustomerDashboard.updateCustomerInfo(updateId, newUsername, newPassword);
                System.out.println("Customer information updated successfully.");
            }
        } else {
            System.out.println("ID NOT FOUND");
        }
    }
    
    private boolean userExists(String username, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[1].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return false;
    }
}

