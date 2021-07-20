package _01_configuration;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConfig {

    public final Metadata metadata;

    public HibernateConfig() {

        StandardServiceRegistry standardServiceRegistery = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        metadata = new MetadataSources(standardServiceRegistery)
                .getMetadataBuilder()
                .build();
    }
}
