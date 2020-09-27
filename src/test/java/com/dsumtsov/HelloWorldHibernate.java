package com.dsumtsov;

import com.dsumtsov.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelloWorldHibernate {

    @Test
    public void storeAndLoadMessage() {

        SessionFactory sessionFactory = new MetadataSources(
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build()
        ).buildMetadata().buildSessionFactory();

        {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Message message = new Message();
            message.setText("Hello World!");

            session.persist(message);
            session.getTransaction().commit();
            session.close();
        }
        {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            List<Message> messages = session.createQuery("FROM Message ").list(); // HQL

            assertEquals(1, messages.size());
            assertEquals("Hello World!", messages.get(0).getText());

            session.getTransaction().commit();
            session.close();
        }

    }
}
