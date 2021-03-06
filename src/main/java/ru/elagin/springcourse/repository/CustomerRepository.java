package ru.elagin.springcourse.repository;

import org.springframework.stereotype.Component;
import ru.elagin.springcourse.exception.RepositoryException;
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

        connection = ConnectionManager.get();
    }

    public List<Customer> index() {
        List<Customer> customers = new ArrayList<>();
        Customer customer;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(INDEX_SQL);

            while (resultSet.next()) {
                customer = getCustomer(resultSet);
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throw new RepositoryException(throwables);
        }
        return customers;
    }

    public Customer show(int id) {
        Customer customer;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_SQL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            customer = getCustomer(resultSet);

        } catch (SQLException throwables) {
            throw new RepositoryException(throwables);
        }
        return customer;
    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setTabnum(resultSet.getInt("tabnum"));
        customer.setName(resultSet.getString("name"));
        customer.setSurname(resultSet.getString("surname"));
        customer.setEmail(resultSet.getString("email"));
        customer.setBirth(resultSet.getDate("birth").toLocalDate());

        return customer;
    }

    public void save(Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL);
            setPreparedStatement(customer, preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new RepositoryException(throwables);
        }
    }

    public void update(int id, Customer updatedCustomer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            setPreparedStatement(updatedCustomer, preparedStatement);
            preparedStatement.setInt(6, updatedCustomer.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new RepositoryException(throwables);
        }
    }

    private void setPreparedStatement(Customer customer, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, customer.getTabnum());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getSurname());
        preparedStatement.setString(4, customer.getEmail());
        preparedStatement.setDate(5, Date.valueOf(customer.getBirth()));
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new RepositoryException(throwables);
        }
    }
}
