package _03_persistence;
import _01_configuration.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;

public class Service <T> implements AutoCloseable{

    HibernateConfig hibernateConfig;
    Session session;

    public Service() {
        hibernateConfig = new HibernateConfig();
        SessionFactory sessionFactory = hibernateConfig.metadata.getSessionFactoryBuilder().build();
        session = sessionFactory.openSession();
    }

    //@Transactional -> doesn't have to cope with transaction rollback
    public void insert(T item) {
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(item); // Create - insert
        try {
            transaction.commit();
        }
        catch (Exception exception) {
                System.out.println("An error occurred, transaction is rolling back");
                transaction.rollback();
        }
    }

    @Transactional
    public T getEntityById (T item, int id) {
        Transaction transaction = session.getTransaction();
        transaction.begin();
        return (T) session.get(item.getClass(),id);// Read - getByID
    }

    @Transactional
    public void updateEntityById (T item, int id) {
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.update(item.getClass().toString(),id); // Update
        transaction.commit();
    }

    @Transactional
    public void deleteEntityById (T item,int id) {
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(item.getClass().toString(),id);
        transaction.commit();
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
