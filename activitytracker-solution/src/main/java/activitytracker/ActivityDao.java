package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ActivityDao {

    private EntityManagerFactory entityManagerFactory;

    public ActivityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveActivity(Activity activity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(activity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Activity> listActivities() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Activity> activities = entityManager
                .createQuery("select a from Activity a", Activity.class)
                .getResultList();
        entityManager.close();
        return activities;
    }

    public Activity findActivityById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager.find(Activity.class, id);
        entityManager.close();
        return activity;
    }

    public void deleteActivity(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager.getReference(Activity.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(activity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateActivity(long id, String description) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Activity activity = entityManager.find(Activity.class, id);
        activity.setDescription(description);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Activity findActivityByIdWithLabels(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager
                .createQuery("select a from Activity a left outer join fetch a.labels where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.close();
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager
                .createQuery("select a from Activity a left outer join fetch a.trackPoints where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.close();
        return activity;
    }

    public void addTrackPoint(long id, TrackPoint trackPoint) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Activity activity = entityManager.find(Activity.class, id);
        trackPoint.setActivity(activity);
        entityManager.persist(trackPoint);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
