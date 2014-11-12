package framework;

import org.hibernate.Session;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class HibernateMockHelper {

  public static <E> List<E> getDeletedEntities(Session hibernateMock) {
    ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
    verify(hibernateMock).delete(captor.capture());
    return (List<E>) captor.getAllValues();
  }

  public static <E> List<E> getSavedEntities(Session hibernateMock) {
    ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
    verify(hibernateMock).save(captor.capture());
    return (List<E>) captor.getAllValues();
  }

  public static <E> List<E> getUpdatedEntities(Session hibernateMock) {
    ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
    verify(hibernateMock, atLeastOnce()).update(captor.capture());
    return (List<E>) captor.getAllValues();
  }
}
