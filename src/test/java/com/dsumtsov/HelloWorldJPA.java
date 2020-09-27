package com.dsumtsov;

import com.dsumtsov.model.Message;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelloWorldJPA {

    @Test
    public void storeAndLoadMessage() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestExamplesJPA");

        {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Message message = new Message();
            message.setText("Hello World!");

            em.persist(message);
            em.getTransaction().commit();
            em.close();
        }
        {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            List<Message> messages = em.createQuery("select m from Message m").getResultList(); // JPQL

            assertEquals(1, messages.size());
            assertEquals("Hello World!", messages.get(0).getText());
            em.close();
        }

        emf.close();
    }
}
