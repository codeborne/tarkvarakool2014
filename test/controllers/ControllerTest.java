package controllers;

import framework.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static framework.Messages.DEFAULT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class ControllerTest<T extends Controller> {

  protected HttpSession session = mock(HttpSession.class);
  protected Session hibernate = mock(Session.class, RETURNS_DEEP_STUBS);
  protected Transaction transaction = mock(Transaction.class);
  protected HttpServletRequest request = mock(HttpServletRequest.class);
  protected Messages.Resolver messages = new Messages(false).getResolverFor(DEFAULT);
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
    this.controller.messages = messages;
    this.controller.request = request;
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

  public <E> E getDeletedEntity() {
    return getSingleItem(getDeletedEntities());
  }

  public <E> E getSavedEntity() {
    return getSingleItem(getSavedEntities());
  }

  public <E> E getUpdatedEntity() {
    return getSingleItem(getUpdatedEntities());
  }

  public <E> List<E> getDeletedEntities() {
    return HibernateMockHelper.getDeletedEntities(hibernate);
  }

  public <E> List<E> getSavedEntities() {
    return HibernateMockHelper.getSavedEntities(hibernate);
  }

  public <E> List<E> getUpdatedEntities() {
    return HibernateMockHelper.<E>getUpdatedEntities(hibernate);
  }

  private <E> E getSingleItem(List<E> list) {
    assertEquals(1, list.size());
    return list.get(0);
  }
}
