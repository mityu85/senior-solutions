package activitytracker;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Activity {



    @TableGenerator(name = "activity_gen",
            table = "act_id_gen",
            pkColumnName = "id_gen",
            valueColumnName = "id_val")
    @Id
    @GeneratedValue(generator = "activity_gen")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> labels;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(name = "activity_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void setCreatedAtBeforePersist() {
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdateAtAfterUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
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
