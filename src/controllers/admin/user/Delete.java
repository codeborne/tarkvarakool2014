package controllers.admin.user;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.User;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class Delete extends UserAwareController {
  public List userNames  = new ArrayList<>();
  public String username;

  @Override
  @Role("admin")
  public Result get() {
    userNames = hibernate.createCriteria(User.class).setProjection(Projections.property("username")).list();
    return render();
  }

  @Override @Role("admin")
  public Result post() {
    User user = (User) hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
    if(user!=null) {
      hibernate.delete(user);
    }
    return redirect(Delete.class);
  }
}
