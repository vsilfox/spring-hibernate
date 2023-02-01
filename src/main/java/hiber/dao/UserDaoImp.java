package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUserByCarModelAndSerial(String model, int series) {
        List<User> returnList = new ArrayList<>();

        String hql = "From User user left join Car car on user.car = car.user " +
                     "where car.model = :model and car.series = :series";

        Query<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("model", model);
        query.setParameter("series", series);

        List<User> resultList = query.list();

        for (Object object : resultList) {
            Object[] row = (Object[]) object;
            returnList.add((User) row[0]);
        }
        return returnList;
    }
}
