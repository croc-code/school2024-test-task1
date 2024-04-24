package com.froleod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void generateReportWithEmptyOrderListTest() {
        List<Order> orders = new ArrayList<>();
        String expectedReport = "{\"months\": []}";
        String actualReport = App.generateReport(orders);
        assertEquals(expectedReport, actualReport);
    }
}
