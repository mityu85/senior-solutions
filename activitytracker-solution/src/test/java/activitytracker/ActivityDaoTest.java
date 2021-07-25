package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    public void testSaveAndUpdate() {
        Activity hiking = new Activity(LocalDateTime.of(
                2021, 2, 2, 18, 0, 0), "hiking", ActivityType.HIKING);
        activityDao.saveActivity(hiking);
        long id = hiking.getId();
        activityDao.updateActivity(id, "dangerous hiking");
        Activity modifiedHiking = activityDao.findActivityById(id);
        assertEquals("dangerous hiking", modifiedHiking.getDescription());
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

    @Test
    public void testLabels() {
        Activity running = new Activity(LocalDateTime.of(
                2021, 4, 28, 10, 30, 0), "running", ActivityType.RUNNING);
        running.setLabels(List.of("half Marathon", "Vivacity"));
        activityDao.saveActivity(running);

        Activity runningWithLabels = activityDao.findActivityByIdWithLabels(running.getId());
        assertEquals(List.of("half Marathon", "Vivacity"), runningWithLabels.getLabels());
    }

    @Test
    public void testTrackPoints() {
        TrackPoint t1 = new TrackPoint(LocalDate.of(2021, 5, 17), 45.789, 98.456);
        TrackPoint t2 = new TrackPoint(LocalDate.of(2021, 5, 18), 75.789, 18.456);

        Activity running = new Activity(LocalDateTime.of(
                2021, 4, 28, 10, 30, 0), "running", ActivityType.RUNNING);

        running.addTrackPoint(t1);
        running.addTrackPoint(t2);

        activityDao.saveActivity(running);

        Activity runningWithTrackPoints = activityDao.findActivityByIdWithTrackPoints(running.getId());
        assertEquals(2, runningWithTrackPoints.getTrackPoints().size());
    }

    @Test
    public void testCoordinatesAfterDate() {
        TrackPoint t1 = new TrackPoint(LocalDate.of(2021, 5, 17), 45.789, 98.456);
        TrackPoint t2 = new TrackPoint(LocalDate.of(2021, 5, 18), 75.789, 18.456);
        TrackPoint t3 = new TrackPoint(LocalDate.of(2021, 5, 19), 12.789, 78.456);
        TrackPoint t4 = new TrackPoint(LocalDate.of(2017, 5, 18), 75.789, 18.456);

        Activity running = new Activity(LocalDateTime.of(
                2016, 4, 28, 10, 30, 0), "running", ActivityType.RUNNING);

        running.addTrackPoint(t1);
        running.addTrackPoint(t2);
        running.addTrackPoint(t3);
        running.addTrackPoint(t4);

        activityDao.saveActivity(running);

        List<CoordinateDto> data =
                activityDao.findTrackPointCoordinatesByDate(LocalDate.of(2018, 1, 1),
                        1, 2);

        assertEquals(2, data.size());
        assertEquals(12.789, data.get(1).getLat());
    }

    @Test
    public void testTrackPointCountByActivity() {
        TrackPoint t1 = new TrackPoint(LocalDate.of(2021, 5, 17), 45.789, 98.456);
        TrackPoint t2 = new TrackPoint(LocalDate.of(2021, 5, 18), 75.789, 18.456);
        TrackPoint t3 = new TrackPoint(LocalDate.of(2021, 5, 19), 12.789, 78.456);
        TrackPoint t4 = new TrackPoint(LocalDate.of(2017, 5, 18), 75.789, 18.456);

        Activity running = new Activity(LocalDateTime.of(
                2016, 4, 28, 10, 30, 0), "running", ActivityType.RUNNING);

        running.addTrackPoint(t1);
        running.addTrackPoint(t2);
        running.addTrackPoint(t3);
        running.addTrackPoint(t4);

        activityDao.saveActivity(running);

        List<Object[]> data = activityDao.findTrackPointCountByActivity();

        assertEquals(1, data.size());
        assertEquals("running", data.get(0)[0]);
    }
}
