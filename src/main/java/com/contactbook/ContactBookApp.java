package com.contactbook;

import java.util.List;
import java.util.Scanner;

import com.contactbook.dao.ContactDAO;
import com.contactbook.model.Contact;

public class ContactBookApp {

    static Scanner sc = new Scanner(System.in);

    static ContactDAO dao = new ContactDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          CONTACT BOOK");
            System.out.println("=================================");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts (A-Z)");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Exit");
            System.out.println("=================================");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    addContact();
                    break;

                case 2:
                    viewContacts();
                    break;

                case 3:
                    searchContact();
                    break;

                case 4:
                    updateContact();
                    break;

                case 5:
                    deleteContact();
                    break;

                case 6:
                    System.out.println("Thank you for using Contact Book!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    // ================= ADD CONTACT =================

    public static void addContact() {

        System.out.println();
        System.out.println("---------- ADD CONTACT ----------");

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter address: ");
        String address = sc.nextLine();

        Contact contact = new Contact(
                name,
                phone,
                email,
                address
        );

        boolean result = dao.addContact(contact);

        if (result) {
            System.out.println("Contact added successfully!");
        } else {
            System.out.println("Failed to add contact.");
        }
    }


    // ================= VIEW CONTACTS =================

    public static void viewContacts() {

        System.out.println();
        System.out.println("---------- CONTACT LIST (A-Z) ----------");

        List<Contact> contacts = dao.getAllContacts();

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.printf(
                "%-5s %-20s %-15s %-25s %-20s%n",
                "ID",
                "NAME",
                "PHONE",
                "EMAIL",
                "ADDRESS"
        );

        System.out.println(
                "--------------------------------------------------------------------------------"
        );

        for (Contact contact : contacts) {

            System.out.printf(
                    "%-5d %-20s %-15s %-25s %-20s%n",
                    contact.getId(),
                    contact.getName(),
                    contact.getPhone(),
                    contact.getEmail(),
                    contact.getAddress()
            );
        }
    }


    // ================= SEARCH CONTACT =================

    public static void searchContact() {

        System.out.println();
        System.out.println("---------- SEARCH CONTACT ----------");

        System.out.print("Enter name to search: ");

        String name = sc.nextLine();

        List<Contact> contacts = dao.searchContact(name);

        if (contacts.isEmpty()) {
            System.out.println("No contact found.");
            return;
        }

        System.out.println();

        for (Contact contact : contacts) {

            System.out.println("ID      : " + contact.getId());
            System.out.println("Name    : " + contact.getName());
            System.out.println("Phone   : " + contact.getPhone());
            System.out.println("Email   : " + contact.getEmail());
            System.out.println("Address : " + contact.getAddress());

            System.out.println("-----------------------------------");
        }
    }


    // ================= UPDATE CONTACT =================

    public static void updateContact() {

        System.out.println();
        System.out.println("---------- UPDATE CONTACT ----------");

        System.out.print("Enter contact ID: ");

        int id;

        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Enter new phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        System.out.print("Enter new address: ");
        String address = sc.nextLine();

        Contact contact = new Contact(
                id,
                name,
                phone,
                email,
                address
        );

        boolean result = dao.updateContact(contact);

        if (result) {
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Contact not found or update failed.");
        }
    }


    // ================= DELETE CONTACT =================

    public static void deleteContact() {

        System.out.println();
        System.out.println("---------- DELETE CONTACT ----------");

        System.out.print("Enter contact ID: ");

        int id;

        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        System.out.print("Are you sure you want to delete this contact? (yes/no): ");

        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {

            boolean result = dao.deleteContact(id);

            if (result) {
                System.out.println("Contact deleted successfully!");
            } else {
                System.out.println("Contact not found.");
            }

        } else {
            System.out.println("Delete cancelled.");
        }
    }
}	