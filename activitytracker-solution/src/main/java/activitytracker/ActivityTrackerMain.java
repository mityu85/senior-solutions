package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ActivityTrackerMain {

    public static void main(String[] args) {

        Activity biking = new Activity(LocalDateTime.of(
                2021, 3, 16, 12, 15, 0), "biking", ActivityType.BIKING);
        Activity hiking = new Activity(LocalDateTime.of(
                2021, 2, 2, 18, 0, 0), "hiking", ActivityType.HIKING);
        Activity basketball = new Activity(LocalDateTime.of(
                2021, 5, 18, 8, 2, 0), "basketball", ActivityType.BASKETBALL);
        Activity running = new Activity(LocalDateTime.of(
                2021, 4, 28, 10, 30, 0), "running", ActivityType.RUNNING);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(biking);
        entityManager.persist(hiking);
        entityManager.persist(basketball);
        entityManager.persist(running);
        entityManager.getTransaction().commit();

        System.out.println(biking.getId());
        System.out.println(hiking.getId());
        System.out.println(basketball.getId());
        System.out.println(running.getId());
    }
}
