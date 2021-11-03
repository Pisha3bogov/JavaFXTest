package ru.sapteh;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.dao.impl.UserDaoImpl;
import ru.sapteh.model.User;

public class Test {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<User, Integer> userIntegerDao = new UserDaoImpl(factory);

        User user = new User("Roman","Shutov",19);
        userIntegerDao.save(user);
    }
}
