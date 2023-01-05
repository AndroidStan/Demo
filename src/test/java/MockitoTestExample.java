import com.example.connectivity.DBConnector;
import com.example.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoTestExample {
    DBConnector dbConnector = mock(DBConnector.class);

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        Customer customer = new Customer("Bai", "Nanai");

        when(dbConnector.readRecordBySearchString("", "", "")).thenReturn(customer);
        assertEquals(customer, dbConnector.readRecordBySearchString("","",""));
    }
}
