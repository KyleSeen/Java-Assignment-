package com.mycompany.javaass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaAss {

    public static void main(String[] args) throws IOException {
        List<Vendor> vendors = new ArrayList<>();
        loadVendorsAndMenu(vendors);
        loadOrders(vendors);
        List<CustomerDashboard> customers = loadCustomersFromFile(vendors);

        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        boolean continueProgram = true;

        while (continueProgram) {
            System.out.println("\n=====================================");
            System.out.println("            Main Menu                ");
            System.out.println("=====================================");
            System.out.println(" 1. Admin                          ");
            System.out.println(" 2. Vendor                         ");
            System.out.println(" 3. Runner                         ");
            System.out.println(" 4. Customer                       ");
            System.out.println(" 0. Exit                           "); 
            System.out.println("=====================================");
            System.out.print("Enter Number of Choice: ");
            int input = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (input) {
                case 1:
                    handleAdminLogin(admin, scanner);
                    break;
                case 2:
                    handleVendorLogin(vendors, scanner);
                    break;
                case 3:
                    handleRunnerLogin(scanner);
                    break;
                case 4:
                    handleCustomerLogin(customers, vendors, scanner);
                    break;
                case 0:
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Invalid choice. Exiting...");
                    continueProgram = false;
            }
        }
    }

    private static void handleAdminLogin(Admin admin, Scanner scanner) throws IOException {
        System.out.println("\n=====================================");
        System.out.println("        Admin Login         ");
        System.out.println("=====================================");
        System.out.print("Enter Admin Name: ");
        String username = scanner.nextLine();

        if (username.equals(admin.getAdminUsername())) {
            System.out.print("Enter Admin Password: ");
            String password = scanner.nextLine();

            if (password.equals(admin.getAdminPassword())) {
                System.out.println("Login Successful!\nWelcome Admin\n");
                admin.runAdminFunctions(true);
            } else {
                System.out.println("Incorrect Password. Please Retry.");
            }
        } else {
            System.out.println("Username does not exist.");
        }
    }

    private static void handleVendorLogin(List<Vendor> vendors, Scanner scanner) {
        for (Vendor vendor : vendors) {
            System.out.println(vendor);
            System.out.println("------------");
        }

        System.out.print("Enter the Vendor ID to work with: ");
        int selectedVendorId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String vendorPassword = Vendor.getVendorPassword(selectedVendorId);
        if (vendorPassword != null) {
            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            if (enteredPassword.equals(vendorPassword)) {
                // Password is correct, proceed with vendor operations
                for (Vendor vendor : vendors) {
                    if (vendor.getVendorId() == selectedVendorId) {
                        System.out.println("\nSelected Vendor: " + vendor.getVendorName());
                        vendor.handleVendorOperations();
                        break;
                    }
                }
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("Invalid Vendor ID.");
        }
    }

    private static void handleRunnerLogin(Scanner scanner) {
        System.out.print("Enter Runner ID: ");
        int runnerId = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter Runner password: ");
        String password = scanner.nextLine();

        if (validateRunnerCredentials(runnerId, password)) {
            System.out.println("Runner identified successfully.");
            DeliveryRunner.main_Page(runnerId);
        } else {
            System.out.println("Invalid Runner ID or Password.");
        }
    }

    private static void handleCustomerLogin(List<CustomerDashboard> customers, List<Vendor> vendors, Scanner scanner) {
        System.out.print("Enter the Customer ID: ");
        int selectedCustomerId = scanner.nextInt();
        scanner.nextLine(); 

        CustomerDashboard selectedCustomer = null;

        for (CustomerDashboard customer : customers) {
            if (customer.getId() == selectedCustomerId) {
                selectedCustomer = customer;
                break;
            }
        }

        if (selectedCustomer != null) {
            System.out.print("Enter Customer Password: ");
            String enteredPassword = scanner.nextLine();

            if (enteredPassword.equals(selectedCustomer.getPassword())) {
                System.out.println("\nSelected Customer: " + selectedCustomer.getName());
                CustomerDashboard dashboard = new CustomerDashboard(
                        selectedCustomer.getId(),
                        selectedCustomer.getName(),
                        selectedCustomer.getPassword(),
                        vendors,
                        selectedCustomer.getBalance()
                );
                dashboard.handleCustomerOperations();
            } else {
                System.out.println("Invalid Customer Password. Please Retry.");
            }
        } else {
            System.out.println("Invalid Customer ID.");
        }
    }

    private static List<Vendor> loadVendorsAndMenu(List<Vendor> vendors) {
        Vendor.resetVendorIdCounter();

        try (BufferedReader infoReader = new BufferedReader(new FileReader("vendor_info.txt"))) {
            String line;
            while ((line = infoReader.readLine()) != null) {
                try {
                    String[] info = line.split(",");
                    Vendor vendor = new Vendor(info[1].trim(), info[2].trim());
                    vendors.add(vendor);

                    // Read menu items from the corresponding menu file
                    try (BufferedReader menuReader = new BufferedReader(new FileReader("menu_" + info[0].trim() + ".txt"))) {
                        String menuItemLine;
                        while ((menuItemLine = menuReader.readLine()) != null) {
                            String[] menuItem = menuItemLine.split(";");
                            vendor.addItemToMenu(new FoodItem(menuItem[0].trim(), menuItem[1].trim(), Double.parseDouble(menuItem[2].trim())));
                        }
                    }

                } catch (NumberFormatException | IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    private static List<Vendor> loadOrders(List<Vendor> vendors) {
    try (BufferedReader infoReader = new BufferedReader(new FileReader("vendor_info.txt"))) {
        String line;
        while ((line = infoReader.readLine()) != null) {
            try {
                String[] info = line.split(",");
                int vendorId = Integer.parseInt(info[0].trim());

                if (vendorId > 0 && vendorId <= vendors.size()) {
                    Vendor currentVendor = vendors.get(vendorId - 1);
                    try (BufferedReader orderReader = new BufferedReader(new FileReader("order_" + vendorId + ".txt"))) {
                        String orderLine;
                        while ((orderLine = orderReader.readLine()) != null) {
                            String[] orderParts = orderLine.split(";");
                            if (orderParts.length >= 6) {
                                int orderId = Integer.parseInt(orderParts[0].trim());
                                String customerName = orderParts[1].trim();
                                String status = orderParts[2].trim();
                                String deliveryStatus = orderParts[5].trim();
                                LocalDate orderDate = LocalDate.parse(orderParts[4].trim());

                                List<OrderItem> items = new ArrayList<>();
                                String[] itemsString = orderParts[3].split(",");
                                for (String itemString : itemsString) {
                                    String[] parts = itemString.split(":");
                                    if (parts.length == 2) {
                                        String itemName = parts[0].trim();
                                        int quantity = Integer.parseInt(parts[1].trim());
                                        items.add(new OrderItem(itemName, quantity));
                                    }
                                }

                                Order order = new Order(orderId, customerName, status, items, orderDate, deliveryStatus);
                                currentVendor.addOrder(order);
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid Vendor ID in order file: " + vendorId);
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error parsing order data: " + e.getMessage());
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading vendor file: " + e.getMessage());
    }
    return vendors;
    }


    private static List<CustomerDashboard> loadCustomersFromFile(List<Vendor> vendors) throws IOException {
        List<CustomerDashboard> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("customer_info.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String password = parts[2];
                double balance = Double.parseDouble(parts[3]); // Assuming balance is at index 3
                CustomerDashboard customer = new CustomerDashboard(id, name, password, vendors, balance);
                customers.add(customer);
            }
        }

        for (CustomerDashboard customer : customers) {
            for (Vendor vendor : vendors) {
                vendor.addCustomer(customer);
            }
        }

        return customers;
    }
    

    private static boolean validateRunnerCredentials(int runnerId, String password) {
    String filename = "runner_info.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[0].trim().equals(String.valueOf(runnerId)) && parts[2].trim().equals(password)) {
                return true; 
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading from " + filename + ": " + e.getMessage());
    }
    return false; 
    }
}