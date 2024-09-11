package epam.com.gymapplication.dao.impl;


import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.UserDAO;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) throws DaoException {
        entityManager.persist(user);

    }

    @Override
    public User findById(Long id) throws DaoException {
        Query findById = entityManager.createQuery("select u from User u where u.id = :id");
        findById.setParameter("id", id);

        return (User) findById.getSingleResult();
    }

    @Override
    @Transactional
    public void update(User user) throws DaoException {
        User byId = findById(user.getId());
        if (byId != null) {
            entityManager.merge(user);
        }
        throw new DaoException("Entity already exists");

    }

    @Override
    public void delete(User user) throws DaoException {
        entityManager.remove(user);



    }

    @Override
    public List<User> findAll() throws DaoException {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findByUsername(String username) throws DaoException {
        Query findByName = entityManager.createQuery("select u from User u where u.username = :username");
        findByName.setParameter("username", username);
        return (User) findByName.getSingleResult();
    }

    @Override
    public User findByFirstName(String firstname) throws DaoException {
        Query findByName = entityManager.createQuery("select u from User u where u.firstname = :firstname");
        findByName.setParameter("firstname", firstname);
        return (User) findByName.getSingleResult();
    }

    @Override
    public User findByLastName(String lastname) throws DaoException {
        Query findByName = entityManager.createQuery("select u from User u where u.lastname = :lastname");
        findByName.setParameter("lastname", lastname);
        return (User) findByName.getSingleResult();
    }
}
