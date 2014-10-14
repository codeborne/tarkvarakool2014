package framework;

import org.hibernate.Session;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.verify;

public class HibernateMockHelper {

  public static List<Object> getDeletedEntities(Session hibernateMock) {
    ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
    verify(hibernateMock).delete(captor.capture());
    return captor.getAllValues();
  }

  public static List<Object> getSavedEntities(Session hibernateMock) {
    ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
    verify(hibernateMock).save(captor.capture());
    return captor.getAllValues();
  }

}
