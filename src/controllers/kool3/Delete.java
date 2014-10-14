package controllers.kool3;

import framework.Controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Delete extends Controller {
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public void post()
    {

    }
}
