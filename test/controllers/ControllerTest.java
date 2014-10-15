package controllers;

import framework.Controller;
import framework.HibernateMockHelper;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public abstract class ControllerTest<T extends Controller> {

  protected Session hibernate = mock(Session.class);
  protected T controller;

  public ControllerTest() {
    Type genericSuperclass = getClass().getGenericSuperclass();
    if (!(genericSuperclass instanceof ParameterizedType)) return;
    ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    if (actualTypeArguments.length != 1) return;
    this.controller = createController(actualTypeArguments[0].getTypeName());
    this.controller.hibernate = hibernate;
  }

  private T createController(String controllerClassName) {
    try {
      //noinspection unchecked
      return (T) Class.forName(controllerClassName).newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create controller", e);
    }
  }

  public Object getDeletedEntity() {
    return getSingleItem(getDeletedEntities());
  }

  public Object getSavedEntity() {
    return getSingleItem(getSavedEntities());
  }

  public Object getUpdatedEntity() {
    return getSingleItem(getUpdatedEntities());
  }

  public List<Object> getDeletedEntities() {
    return HibernateMockHelper.getDeletedEntities(hibernate);
  }

  public List<Object> getSavedEntities() {
    return HibernateMockHelper.getSavedEntities(hibernate);
  }

  public List<Object> getUpdatedEntities() {
    return HibernateMockHelper.getUpdatedEntities(hibernate);
  }

  private Object getSingleItem(List<Object> list) {
    assertEquals(1, list.size());
    return list.get(0);
  }
}
