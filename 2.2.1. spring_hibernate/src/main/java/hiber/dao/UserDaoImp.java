package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void addCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public User getUserCar(String model, int series) {

        String sql = "from Car e where e.model = :model and e.series = :series";

        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("model", model);
        query.setParameter("series", series);

        String firstName = query.getSingleResult().getUser().getFirstName();
        String lastName = query.getSingleResult().getUser().getLastName();
        String email = query.getSingleResult().getUser().getEmail();

        return new User(firstName, lastName, email);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

}
