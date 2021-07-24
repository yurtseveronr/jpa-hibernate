package _03_persistence;
import _01_configuration.HibernateConfig;
import _02_mapping.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

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

    public List<User> getAllUsers () {
        // HQL query
        String query_hql = "from entity_person"; // you must use entity name (default : class name)
        Query query = session.createQuery(query_hql);
        return query.getResultList();
    }

    public User getUserByIdWithHQl(int id) {
        String query_hql = "from entity_person where id = : id"; // :id query parameter
        Query query = session.createQuery(query_hql);
        query.setParameter("id",id); // passing parameter to query, id refers to :id
        return  (User) query.getSingleResult();
    }

    public void deleteUSerByIdWithHQl(int id) {
        Transaction transaction = session.getTransaction();
        transaction.begin();

        String query_hql = "delete entity_person where id = : id";
        Query query = session.createQuery(query_hql);
        query.setParameter("id",id);
        final int i = query.executeUpdate();
        System.out.println(i); // the numbers of entities deleted or updated, rows affected

        transaction.commit();
    }




    @Override
    public void close() throws Exception {
        session.close();
    }
}
