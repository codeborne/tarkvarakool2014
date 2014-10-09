package framework;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.time.DateUtils.parseDate;

public class Binder {
  private String dateFormat;

  public Binder(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  void bindRequestParameters(Object controller, Map<String, String[]> params) {
    Class clazz = controller.getClass();
    for (Map.Entry<String, String[]> param : params.entrySet()) {
      try {
        Field field = clazz.getDeclaredField(param.getKey());
        field.setAccessible(true);
        setFieldValue(controller, field, param.getValue());
      }
      catch (NoSuchFieldException ignore) {
      }
    }
  }

  void setFieldValue(Object controller, Field field, String[] values) {
    try {
      Class<?> type = field.getType();
      Object value;

      if (String.class.isAssignableFrom(type))
        value = values[0];
      else if (String[].class.isAssignableFrom(type))
        value = values;
      else if (List.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type) || Iterable.class.isAssignableFrom(type))
        value = asList(values);
      else if (Date.class.isAssignableFrom(type))
        value = parseDate(values[0], dateFormat, "yyyy-MM-dd");
      else {
        if (type.isPrimitive()) type = ClassUtils.primitiveToWrapper(type);
        value = type.getConstructor(String.class).newInstance(values[0]);
      }
      field.set(controller, value);
    }
    catch (InvocationTargetException e) {
      recordBindError(controller, field.getName(), e.getCause());
    }
    catch (Exception e) {
      recordBindError(controller, field.getName(), e);
    }
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  private void recordBindError(Object controller, String name, Throwable e) {
    if (controller instanceof Controller) {
      ((Controller) controller).errors.put(name, e);
    }
  }
}
