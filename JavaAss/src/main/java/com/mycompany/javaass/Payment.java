/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.javaass;

import java.io.*;

public class Payment {
    private String customerId;
    private double amountPaid;

    public Payment(String customerId, double amountPaid) {
        this.customerId = customerId;
        this.amountPaid = amountPaid;
    }

    public boolean processPayment(Customer customer, double amount) {
        if (customer.deductCredit(amount)) {
            this.amountPaid = amount; // Store the paid amount
            savePayment(customer.getCustomerId(), amount);
            printReceipt(); // Print receipt after payment success
            return true;
        }
        System.out.println("Payment failed: Insufficient credit.");
        return false;
    }

    private void savePayment(String customerId, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("payments.txt", true))) {
            writer.write(customerId + "," + amount);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving payment: " + e.getMessage());
        }
    }

    public void printReceipt() {
        System.out.println("Payment Successful!");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Amount Paid: $" + amountPaid);
        System.out.println("Thank you for using our service.");
    }

    // Embedded Customer class for handling customer credits
    public static class Customer {
        private String customerId;
        private double creditBalance;

        public Customer(String customerId, double creditBalance) {
            this.customerId = customerId;
            this.creditBalance = creditBalance;
        }

        public String getCustomerId() {
            return customerId;
        }

        public boolean deductCredit(double amount) {
            if (creditBalance >= amount) {
                creditBalance -= amount;
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("C123", 100.0); // Customer with $100 balance
        Payment payment = new Payment(customer.getCustomerId(), 0.0);

        payment.processPayment(customer, 30.0); // Attempt to pay $30
    }
}
