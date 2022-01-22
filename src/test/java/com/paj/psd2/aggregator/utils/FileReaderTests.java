package com.paj.psd2.aggregator.utils;

import com.paj.psd2.aggregator.client.generated.model.TransactionsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileReaderTests {

    private static final String TRANSACTIONS_MOCK_FILE_LOCATION = "mock/transactions.json";

    @Test
    public void testFileReaderTest(){
        TransactionsResponse transactionsResponse = FileReader
                .readFromFile(TRANSACTIONS_MOCK_FILE_LOCATION, TransactionsResponse.class);

        Assertions.assertNotNull(transactionsResponse);
        Assertions.assertEquals(5, transactionsResponse.getTransactions().getPending().size());
        Assertions.assertEquals(1, transactionsResponse.getTransactions().getBooked().size());
    }
}
