package com.example.connectivity;

import com.example.model.Customer;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DBConnectorTest {
    DBConnector dbConnector = new DBConnector();

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws SQLException {
        dbConnector.databaseConnection = mock(Connection.class);

        Statement statementMock = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(statementMock.executeQuery(any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        Customer customer = new Customer("Bai", "Noi");
        //when(resultSet.getString(any())).thenReturn("Bai");
        when(resultSet.getString("firstname")).thenReturn(customer.getFirstName());
        when(resultSet.getString("lastname")).thenReturn(customer.getLastName());
        when(dbConnector.databaseConnection.createStatement()).thenReturn(statementMock);

        assertThat(dbConnector.readRecordBySearchString("x", "y", "z")).usingRecursiveComparison().isEqualTo(customer);
        //verify that certain methods have been called
        //asserts
        //check for no more interactions
    }
}