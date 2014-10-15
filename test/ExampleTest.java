import org.hibernate.Session;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExampleTest {

  @Test
  public void compute() throws Exception {
    Example example = new Example();

    int result = example.compute(2, 3);

    assertEquals(6, result);
  }

  @Test
  public void name() throws Exception {
    Example example = new Example();
    example.name = "Erik";

    String greeting = example.getGreeting("Hello");

    assertEquals("Hello Erik", greeting);
  }

  @Test
  public void addName() throws Exception {
    Example example = new Example();
    example.name = "Erik";
    ArrayList<String> list = new ArrayList<>();

    example.addName(list);

    assertTrue(list.contains("Erik"));
  }

  @Test
  public void setName() throws Exception {
    Example example = new Example();

    example.setName("Mari");

    assertEquals("Mari", example.name);
  }


  @Test
  public void storeValue() throws Exception {
    Example example = new Example();
    example.values = new ArrayList<>();

    example.storeValue(123);

    assertEquals(123, (int)example.values.get(0));
  }

  @Test
  public void storeData() throws Exception {
    Example example = new Example();
    example.hibernate = mock(Session.class);

    example.storeData("information");

    verify(example.hibernate).save("information");
  }
}
