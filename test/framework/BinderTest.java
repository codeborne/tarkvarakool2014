package framework;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.apache.commons.lang3.time.DateUtils.parseDate;
import static org.junit.Assert.*;

public class BinderTest {
  Binder binder = new Binder("dd.MM.yyyy");

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

  @Test
  public void bindDate() throws Exception {
    class Foo extends Controller {
      private Date date;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("date", new String[]{"11.07.2013"}));
    assertEquals(date("11.07.2013"), controller.date);
  }

  @Test
  public void bindStringArray() throws Exception {
    class Foo extends Controller {
      private String[] strings;
    }
    Foo controller = new Foo();
    String[] value = {"a", "b", "c"};
    binder.bindRequestParameters(controller, singletonMap("strings", value));
    assertArrayEquals(value, controller.strings);
  }

  @Test
  public void bindList() throws Exception {
    class Foo extends Controller {
      private List<String> list;
    }
    Foo controller = new Foo();
    String[] value = {"a", "b", "c"};
    binder.bindRequestParameters(controller, singletonMap("list", value));
    assertEquals(asList(value), controller.list);
  }

  @Test
  public void bindSet() throws Exception {
    class Foo extends Controller {
      private Set<String> set;
    }
    Foo controller = new Foo();
    String[] value = {"a", "b", "c"};
    binder.bindRequestParameters(controller, singletonMap("set", value));
    assertEquals(new LinkedHashSet<>(asList(value)), controller.set);
  }

  @Test
  public void bindIntArray() throws Exception {
    class Foo extends Controller {
      private int[] ints;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("ints", new String[]{"1", "2", "3"}));
    assertArrayEquals(new int[]{1, 2, 3}, controller.ints);
  }

  @Test
  public void bindCollectionOfBigDecimals() throws Exception {
    class Foo extends Controller {
      private Collection<BigDecimal> col;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("col", new String[]{"1", "2"}));
    assertEquals(asList(new BigDecimal("1"), new BigDecimal("2")), controller.col);
  }

  @Test
  public void bindErrorsAreReported() throws Exception {
    class Foo extends Controller {
      private Integer number;
    }
    Foo controller = new Foo();
    binder.bindRequestParameters(controller, singletonMap("number", new String[]{"zzz"}));
    assertNull(controller.number);
    assertEquals(NumberFormatException.class, controller.errors.get("number").getClass());
  }

  public static Date date(String ddMMyyyy) throws ParseException {
    return parseDate(ddMMyyyy, "dd.MM.yyyy");
  }
}