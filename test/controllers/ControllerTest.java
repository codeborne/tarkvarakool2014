package controllers;

import framework.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpSession;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class ControllerTest<T extends Controller> {

  protected HttpSession session = mock(HttpSession.class);
  protected Session hibernate = mock(Session.class);
  protected Transaction transaction = mock(Transaction.class);
  protected T controller;

  public ControllerTest() {
    Type genericSuperclass = getClass().getGenericSuperclass();
    if (!(genericSuperclass instanceof ParameterizedType)) return;
    ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    if (actualTypeArguments.length != 1) return;
    this.controller = createController(actualTypeArguments[0].getTypeName());
    this.controller.session = session;
    this.controller.hibernate = hibernate;
    when(hibernate.getTransaction()).thenReturn(transaction);
  }

  private T createController(String controllerClassName) {
    try {
      //noinspection unchecked
      return (T) Class.forName(controllerClassName).newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create controller", e);
    }
  }

  protected void assertRedirect(String target, Result result) {
    assertEquals(Redirect.class, result.getClass());
    assertEquals(target, ((Redirect) result).getPath());
  }

  protected void assertRedirect(Class<? extends Controller> targetClass, Result result) {
    assertEquals(Redirect.class, result.getClass());
    assertEquals(Redirect.createPath(targetClass), ((Redirect) result).getPath());
  }

  protected void assertRender(Result result) {
    assertEquals(Render.class, result.getClass());
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
