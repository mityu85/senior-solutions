package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityDaoTest {

    private ActivityDao activityDao;

    @BeforeEach
    public void init() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(entityManagerFactory);
    }

    @Test
    public void testSaveAndFindById() {
        Activity biking = new Activity(LocalDateTime.of(
                2021, 3, 16, 12, 15, 0), "biking", ActivityType.BIKING);
        activityDao.saveActivity(biking);

        long id = biking.getId();
        Activity anotherActivity = activityDao.findActivityById(id);

        assertEquals("biking", anotherActivity.getDescription());
    }

    @Test
    public void testSaveAndListAll() {
        Activity biking = new Activity(LocalDateTime.of(
                2021, 3, 16, 12, 15, 0), "biking", ActivityType.BIKING);
        Activity hiking = new Activity(LocalDateTime.of(
                2021, 2, 2, 18, 0, 0), "hiking", ActivityType.HIKING);
        Activity basketball = new Activity(LocalDateTime.of(
                2021, 5, 18, 8, 2, 0), "basketball", ActivityType.BASKETBALL);
        Activity running = new Activity(LocalDateTime.of(
                2021, 4, 28, 10, 30, 0), "running", ActivityType.RUNNING);

        activityDao.saveActivity(biking);
        activityDao.saveActivity(hiking);
        activityDao.saveActivity(basketball);
        activityDao.saveActivity(running);

        List<Activity> activities = activityDao.listActivities();

        assertEquals(List.of("biking", "hiking", "basketball", "running"),
                activities.stream().map(Activity::getDescription).collect(Collectors.toList()));
    }

    @Test
    public void testDelete() {
        Activity basketball = new Activity(LocalDateTime.of(
                2021, 5, 18, 8, 2, 0), "basketball", ActivityType.BASKETBALL);
        activityDao.saveActivity(basketball);
        long id = basketball.getId();
        activityDao.deleteActivity(id);
        List<Activity> activities = activityDao.listActivities();
        assertTrue(activities.isEmpty());
    }
}
