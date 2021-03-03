package ru.elagin.springcourse.repository;

import org.springframework.stereotype.Component;
import ru.elagin.springcourse.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRepository {
    private static final String INDEX_SQL = "SELECT id, tabnum, name, surname, email, birth FROM customers ORDER BY id;";
    private static final String SHOW_SQL = "SELECT id, tabnum, name, surname, email, birth FROM customers WHERE id = ? ;";
    private static final String SAVE_SQL = "INSERT INTO customers(tabnum, name, surname, email, birth) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE customers SET tabnum = ?, name = ?, surname = ?, email = ?, birth = ? WHERE id = ?;";
    private static final String DELETE_SQL = "DELETE FROM customers WHERE id = ?;";

    private final Connection connection;

    public CustomerRepository() {

        connection = ConnectionManager.open();
    }

    public List<Customer> index() {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(INDEX_SQL);

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setTabnum(resultSet.getInt("tabnum"));
                customer.setName(resultSet.getString("name"));
                customer.setSurname(resultSet.getString("surname"));
                customer.setEmail(resultSet.getString("email"));
                customer.setBirth(resultSet.getDate("birth").toLocalDate());

                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public Customer show(int id) {
        Customer customer = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_SQL);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setTabnum(resultSet.getInt("tabnum"));
            customer.setName(resultSet.getString("name"));
            customer.setSurname(resultSet.getString("surname"));
            customer.setEmail(resultSet.getString("email"));
            customer.setBirth(resultSet.getDate("birth").toLocalDate());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    public void save(Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL);
            preparedStatement.setInt(1, customer.getTabnum());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getSurname());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setDate(5, java.sql.Date.valueOf(customer.getBirth()));

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Customer updatedCustomer) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setInt(1, updatedCustomer.getTabnum());
            preparedStatement.setString(2, updatedCustomer.getName());
            preparedStatement.setString(3, updatedCustomer.getSurname());
            preparedStatement.setString(4, updatedCustomer.getEmail());
            preparedStatement.setDate(5, java.sql.Date.valueOf(updatedCustomer.getBirth()));
            preparedStatement.setInt(6, updatedCustomer.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
