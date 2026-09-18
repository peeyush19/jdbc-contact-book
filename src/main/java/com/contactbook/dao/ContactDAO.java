package com.contactbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.contactbook.model.Contact;
import com.contactbook.util.DBConnection;

public class ContactDAO {

    // Add Contact
    public boolean addContact(Contact contact) {

        String sql = "INSERT INTO contact (name, phone, email, address) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getAddress());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error while adding contact.");
            System.out.println(e.getMessage());
            return false;
        }
    }


    // View all contacts alphabetically
    public List<Contact> getAllContacts() {

        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT * FROM contact ORDER BY name ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Contact contact = new Contact();

                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));

                contacts.add(contact);
            }

        } catch (Exception e) {
            System.out.println("Error while fetching contacts.");
            System.out.println(e.getMessage());
        }

        return contacts;
    }


    // Search Contact by name
    public List<Contact> searchContact(String name) {

        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT * FROM contact "
                   + "WHERE name LIKE ? "
                   + "ORDER BY name ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Contact contact = new Contact();

                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));

                contacts.add(contact);
            }

        } catch (Exception e) {
            System.out.println("Error while searching contact.");
            System.out.println(e.getMessage());
        }

        return contacts;
    }


    // Update Contact
    public boolean updateContact(Contact contact) {

        String sql = "UPDATE contact SET name = ?, phone = ?, "
                   + "email = ?, address = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getAddress());
            ps.setInt(5, contact.getId());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error while updating contact.");
            System.out.println(e.getMessage());
            return false;
        }
    }


    // Delete Contact
    public boolean deleteContact(int id) {

        String sql = "DELETE FROM contact WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error while deleting contact.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}