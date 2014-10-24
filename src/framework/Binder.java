package framework;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.time.DateUtils.parseDate;

public class Binder {
  private String dateFormat;

  public Binder(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  void bindRequestParameters(Controller controller, Map<String, String[]> params) {
    Class clazz = controller.getClass();
    for (Map.Entry<String, String[]> param : params.entrySet()) {
      try {
        Field field = clazz.getField(param.getKey());
        if (field.getDeclaringClass().isAssignableFrom(Controller.class)) continue;
        setFieldValue(controller, field, param.getValue());
      }
      catch (NoSuchFieldException ignore) {
      }
    }
  }

  void setFieldValue(Controller controller, Field field, String[] values) {
    try {
      field.set(controller, convert(values, field.getType(), field));
    }
    catch (InvocationTargetException e) {
      recordBindError(controller, field.getName(), e.getCause());
    }
    catch (Exception e) {
      recordBindError(controller, field.getName(), e);
    }
  }

  private Object convert(String[] values, Class<?> type, Field field) throws Exception {
    if (String.class == type)
      return values[0];

    if (String[].class == type)
      return values;

    if (type.isArray()) {
      Object array = Array.newInstance(type.getComponentType(), values.length);
      for (int i = 0; i < values.length; i++)
        Array.set(array, i, convert(new String[]{values[i]}, type.getComponentType(), field));
      return array;
    }

    if (List.class == type || Collection.class == type || Iterable.class == type)
      return asList(convertArrayType(values, getGenericType(field)));

    if (Set.class == type)
      return new LinkedHashSet<>(asList(convertArrayType(values, getGenericType(field))));

    if ("".equals(values[0]))
      return null;

    if (Date.class == type)
      return parseDate(values[0], dateFormat, "yyyy-MM-dd");

    // try with String-argument constructor
    if (type.isPrimitive()) type = ClassUtils.primitiveToWrapper(type);
    return type.getConstructor(String.class).newInstance(values[0]);
  }

  private Object[] convertArrayType(String[] values, Class<?> componentType) throws Exception {
    Class<?> arrayType = Array.newInstance(componentType, 0).getClass();
    return (Object[])convert(values, arrayType, null);
  }

  private Class<?> getGenericType(Field field) {
    return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  private void recordBindError(Controller controller, String name, Throwable e) {
    controller.errors.put(name, e);
  }
}
