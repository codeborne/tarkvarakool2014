package framework;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Binder {
  void bindRequestParameters(Object controller, Map<String, String[]> params) {
    Class clazz = controller.getClass();
    for (Map.Entry<String, String[]> param : params.entrySet()) {
      try {
        Field field = clazz.getDeclaredField(param.getKey());
        field.setAccessible(true);
        setFieldValue(controller, field, param.getValue());
      }
      catch (NoSuchFieldException | IllegalAccessException ignore) {
      }
    }
  }

  void setFieldValue(Object controller, Field field, String[] values) throws IllegalAccessException {
    Class type = field.getType();
    if (String.class.isAssignableFrom(type))
      field.set(controller, values[0]);
    else if (String[].class.isAssignableFrom(type))
      field.set(controller, values);
    else {
      try {
        if (type.isPrimitive()) type = ClassUtils.primitiveToWrapper(type);
        Object converted = type.getConstructor(String.class).newInstance(values[0]);
        field.set(controller, converted);
      }
      catch (InstantiationException | InvocationTargetException | NoSuchMethodException e) {
        // todo report binding error
      }
    }
  }
}
