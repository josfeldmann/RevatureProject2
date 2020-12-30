package com.shopsim.dao;


import com.shopsim.controllers.OrderController;
import com.shopsim.models.Item;
import com.shopsim.models.ItemList;
import com.shopsim.models.User;
import com.shopsim.models.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@Transactional
public class OrderRepo {
    private SessionFactory sessionFactory;
    private static final Logger orLog = LogManager.getLogger(OrderRepo.class);

    @PostConstruct
    public void initDB() {
        Session session = sessionFactory.openSession();
    }

    @Autowired
    public OrderRepo(SessionFactory sessionFactory) {
        orLog.info("Creating OrderRepo");
        this.sessionFactory = sessionFactory;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void submitOrder(Order order) {
        orLog.info("Saving Order to Database...");
        Session session = sessionFactory.getCurrentSession();
        session.save(order);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Order getOrderById (int id) {
        Session session = sessionFactory.getCurrentSession();
        orLog.info("Selecting User's Most Recent Order...");
        String hql = "from Order where userId =:id order by id DESC";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setMaxResults(1);
        Order order = (Order) query.list().get(0);
        Hibernate.initialize(order.getItems());
        return order;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Order> getOrdersByUserId (String id) {
        Session session = sessionFactory.getCurrentSession();
        orLog.info("Selecting All User's Orders...");
        String hql = "from Order where userId =:id order by id DESC";
        Query query = session.createQuery(hql);
        query.setInteger("id", Integer.parseInt(id));
        List<Order> orders = query.list();
        for (int i = 0; i < orders.size(); i++) {
            Hibernate.initialize(orders.get(i).getItems());
        }
        return orders;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void saveItem(Item item) {
        orLog.info("Saving Item to Database...");
        Session session = sessionFactory.getCurrentSession();
        session.save(item);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void deleteOrderEntry(int id) {
        Session session = sessionFactory.getCurrentSession();
        orLog.info("Deleting Test Order Entry from Database...");
        String hql = "delete from Order o where o.userId = :id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.executeUpdate();
    }
}
