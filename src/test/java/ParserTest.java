import org.example.Parser;
import org.example.enums.Status;
import org.example.pojo.Order;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {
    private Parser<Order> parser;

    @Before
    public void setUp() {
        //TODO: Происвоить объект на JsonParser
    }

    @Test
    public void whenDeserializeSizeOfListShouldBe6() {
        List<Order> orders = parser.deserialize();
        assertEquals(orders.size(), 6);
    }

    @Test
    public void whenDeserializeFirstElementShouldBeRight() {
        List<Order> orders = parser.deserialize();
        Order order = orders.get(0);
        assertEquals(order.getUser_id(), "3acfb0b7-04bd-4978-be4c-3929372277c1");
        assertEquals(order.getOrdered_at(), LocalDateTime.parse("2023-01-16T13:56:39.492"));
        assertEquals(order.getStatus(), Status.COMPLETED);
        assertEquals(order.getTotal(), 1917.0, 0.00001);
    }

    @Test
    public void whenSerializeNoNullListItWorksCorrectly(){
        List<String> list = List.of("march", "april", "may", "june");
        String json = parser.serialize(list);
        assertEquals(json, "{\"months\":[\"march\",\"april\",\"may\",\"june\"]}");
    }

    @Test
    public void whenSerializeNullListItWorksCorrectly(){
        List<String> list = List.of();
        String json = parser.serialize(list);
        assertEquals(json, "{\"months\":[]}");
    }

    @Test
    public void checkCorrectWorkOfDoCounting(){
        String json = parser.doCounting();
        assertEquals(json, "{\"months\":[\"december\"]}");
    }
}
