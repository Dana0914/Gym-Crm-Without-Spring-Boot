package epam.com.gymapplication.model;

import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "trainer")
public class Trainer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    public Trainer() {

    }

    public Trainer(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "specialization", nullable = false, unique = true)
    private TrainingType trainingType;

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "users_id", unique = true, nullable = false)
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    private Set<Trainee> trainees = new HashSet<>();

    public Set<Trainee> getTrainees() {
        return trainees;
    }
    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Training> trainings = new HashSet<>();

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(id, trainer.id)
                && Objects.equals(trainingType, trainer.trainingType)
                && Objects.equals(user, trainer.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingType, user);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", trainingType=" + trainingType +
                ", user=" + user +
                '}';
    }
}
