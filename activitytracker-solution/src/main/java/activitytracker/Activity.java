package activitytracker;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(name = "activity_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    public Activity() {
    }

    public Activity(LocalDateTime startTime, String description, ActivityType activityType) {
        this.startTime = startTime;
        this.description = description;
        this.activityType = activityType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return startTime;
    }

    public void setStart(LocalDateTime start) {
        this.startTime = start;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", start=" + startTime +
                ", description='" + description + '\'' +
                ", activityType=" + activityType +
                '}';
    }
}
