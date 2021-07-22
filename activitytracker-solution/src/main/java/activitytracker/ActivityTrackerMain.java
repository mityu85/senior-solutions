package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityTrackerMain {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager entityManager = factory.createEntityManager();

        Activity biking = new Activity(LocalDateTime.of(
                2021, 3, 16, 12, 15, 0), "biking", ActivityType.BIKING);
        Activity hiking = new Activity(LocalDateTime.of(
                2021, 2, 2, 18, 0, 0), "hiking", ActivityType.HIKING);
        Activity basketball = new Activity(LocalDateTime.of(
                2021, 5, 18, 8, 2, 0), "basketball", ActivityType.BASKETBALL);
        Activity running = new Activity(LocalDateTime.of(
                2021, 4, 28, 10, 30, 0), "running", ActivityType.RUNNING);

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

        long bikingId = biking.getId();
        long hikingId = hiking.getId();
        long basketballId = basketball.getId();
        long runningId = running.getId();

        biking = entityManager.find(Activity.class, bikingId);
        hiking = entityManager.find(Activity.class, hikingId);
        basketball = entityManager.find(Activity.class, basketballId);
        running = entityManager.find(Activity.class, runningId);

        System.out.println(biking);
        System.out.println(hiking);
        System.out.println(basketball);
        System.out.println(running);

        List<Activity> activities = entityManager
                .createQuery("select a from Activity a", Activity.class)
                .getResultList();
        System.out.println(activities);

        entityManager.getTransaction().begin();
        biking = entityManager.find(Activity.class, bikingId);
        biking.setDescription("long biking");
        entityManager.getTransaction().commit();
        System.out.println(biking);

        entityManager.getTransaction().begin();
        hiking = entityManager.find(Activity.class, hikingId);
        hiking.setDescription("dangerous hiking");
        entityManager.getTransaction().commit();
        System.out.println(hiking);

        entityManager.getTransaction().begin();
        basketball = entityManager.find(Activity.class, basketballId);
        basketball.setDescription("professional basketball");
        entityManager.getTransaction().commit();
        System.out.println(basketball);

        entityManager.getTransaction().begin();
        running = entityManager.find(Activity.class, runningId);
        running.setDescription("fast running");
        entityManager.getTransaction().commit();
        System.out.println(running);

        entityManager.getTransaction().begin();
        running = entityManager.find(Activity.class, runningId);
        entityManager.remove(running);
        entityManager.getTransaction().commit();

        activities = entityManager
                .createQuery("select a from Activity a", Activity.class)
                .getResultList();
        System.out.println(activities);

        entityManager.close();
        factory.close();
    }
}
