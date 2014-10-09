package framework;

import org.junit.Test;

import java.math.BigDecimal;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

public class BinderTest {
  Binder binder = new Binder();

  @Test
  public void bindRequestParametersToFields() throws Exception {
    class Foo extends Controller {
      private String name;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("name", new String[]{"Anton"}));
    assertEquals("Anton", controller.name);
  }

  @Test
  public void bindInt() throws Exception {
    class Foo extends Controller {
      private int count;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("count", new String[]{"123"}));
    assertEquals(123, controller.count);
  }

  @Test
  public void bindInteger() throws Exception {
    class Foo extends Controller {
      private Integer count;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("count", new String[]{"123"}));
    assertEquals(123, (int)controller.count);
  }

  @Test
  public void bindBigDecimal() throws Exception {
    class Foo extends Controller {
      private BigDecimal amount;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("amount", new String[]{"123.45"}));
    assertEquals(new BigDecimal("123.45"), controller.amount);
  }
}