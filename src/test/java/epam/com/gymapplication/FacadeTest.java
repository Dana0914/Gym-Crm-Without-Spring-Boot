package epam.com.gymapplication;


import epam.com.gymapplication.facade.Facade;
import epam.com.gymapplication.model.*;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.service.TrainerService;
import epam.com.gymapplication.service.TrainingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacadeTest {

    @InjectMocks
    private Facade facade;

    @Mock
    private TrainingService trainingService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    private Training training;
    private Training training2;

    private Trainee trainee;
    private Trainee trainee2;

    private Trainer trainer;
    private Trainer trainer2;

    @BeforeEach
    public void setUp() {

        User user = new User();
        user.setId(1L);
        user.setUsername("John.Doe");
        user.setPassword("8&hs-@.");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        training = new Training();
        training.setId(1L);
        training.setTraineeID(1L);
        training.setTrainerID(3L);
        training.setTrainingName("stretching");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingType(TrainingType.STRETCHING);
        training.setTrainingDuration(45);


        training2 = new Training();
        training2.setId(2L);
        training.setTraineeID(1L);
        training.setTrainerID(4L);
        training.setTrainingName("strength");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingType(TrainingType.STRENGTH);
        training.setTrainingDuration(45);

        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUserId(1L);
        trainee.setAddress("Edinburgh street 12");
        trainee.setDateOfBirth(LocalDate.of(2001, 4, 2));
        trainee.setUser(user);

        trainee2 = new Trainee();
        trainee2.setId(2L);
        trainee2.setUserId(2L);
        trainee2.setAddress("Edinburgh street 4");
        trainee2.setDateOfBirth(LocalDate.of(2000, 8, 15));
        trainee2.setUser(user);

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUserId(1L);
        trainer.setUser(user);
        trainer.setSpecialization("aerobics");

        trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setUserId(2L);
        trainer2.setUser(user);
        trainer2.setSpecialization("aerobics");

    }

    @Test
    public void saveTraining_withValidData_returnsValidEntity() {
        facade.saveTraining(training);

        when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.of(training));



        verify(trainingService).saveTraining(training);

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());
        Assertions.assertEquals(training.getId(), trainingById.get().getId());
        Assertions.assertEquals(training.getTrainingName(), trainingById.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), trainingById.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), trainingById.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), trainingById.get().getTrainingDuration());
        Assertions.assertEquals(training.getTrainerID(), trainingById.get().getTrainerID());
        Assertions.assertEquals(training.getTraineeID(), trainingById.get().getTraineeID());

    }

    @Test
    public void saveTrainer_withValidData_returnsValidEntity() {
        facade.saveTrainer(trainer);
        when(trainerService.findTrainerById(trainer.getId())).thenReturn(Optional.of(trainer));


        verify(trainerService).saveTrainer(trainer);

        Optional<Trainer> trainerById = trainerService.findTrainerById(trainer.getId());

        Assertions.assertTrue(trainerById.isPresent());
        Assertions.assertEquals(trainer.getId(), trainerById.get().getId());
        Assertions.assertEquals(trainer.getUserId(), trainerById.get().getUserId());
        Assertions.assertEquals(trainer.getUser(), trainerById.get().getUser());
        Assertions.assertEquals(trainer.getSpecialization(), trainerById.get().getSpecialization());

    }

    @Test
    public void saveTrainee_withValidData_returnsValidEntity() {
        facade.saveTrainee(trainee);
        when(traineeService.findTraineeById(trainee.getId())).thenReturn(Optional.of(trainee));


        verify(traineeService).saveTrainee(trainee);

        Optional<Trainee> traineeById = traineeService.findTraineeById(trainee.getId());

        Assertions.assertTrue(traineeById.isPresent());
        Assertions.assertEquals(trainee.getId(), traineeById.get().getId());
        Assertions.assertEquals(trainee.getUserId(), traineeById.get().getUserId());
        Assertions.assertEquals(trainee.getUser(), traineeById.get().getUser());
        Assertions.assertEquals(trainee.getAddress(), traineeById.get().getAddress());
        Assertions.assertEquals(trainee.getDateOfBirth(), traineeById.get().getDateOfBirth());

    }

    @Test
    public void updateTrainee_withExistingEntity_updatesEntityDetails() {
        facade.saveTrainee(trainee);
        when(traineeService.findTraineeById(trainee.getId())).thenReturn(Optional.of(trainee));

        Optional<Trainee> traineeById1 = traineeService.findTraineeById(trainee.getId());
        Assertions.assertTrue(traineeById1.isPresent());



        trainee2.setId(trainee.getId());

        traineeService.updateTrainee(trainee2);

        when(traineeService.findTraineeById(trainee2.getId())).thenReturn(Optional.of(trainee2));

        Optional<Trainee> updatedTraineeById = traineeService.findTraineeById(trainee2.getId());
        Assertions.assertTrue(updatedTraineeById.isPresent());



        Assertions.assertEquals(updatedTraineeById.get().getId(), trainee2.getId());
        Assertions.assertEquals(updatedTraineeById.get().getUser(), trainee2.getUser());
        Assertions.assertEquals(updatedTraineeById.get().getAddress(), trainee2.getAddress());
        Assertions.assertEquals(updatedTraineeById.get().getId(), trainee2.getId());
        Assertions.assertEquals(updatedTraineeById.get().getDateOfBirth(), trainee2.getDateOfBirth());

    }

    @Test
    public void updateTrainer_withExistingEntity_updatesEntityDetails() {
        facade.saveTrainer(trainer);


        when(trainerService.findTrainerById(trainer.getId())).thenReturn(Optional.of(trainer));


        Optional<Trainer> trainerById = trainerService.findTrainerById(trainer.getId());
        Assertions.assertTrue(trainerById.isPresent());


        trainer2.setId(trainer.getId());

        trainerService.updateTrainer(trainer2);

        when(trainerService.findTrainerById(trainer2.getId())).thenReturn(Optional.of(trainer2));


        Optional<Trainer> updatedTrainerById = trainerService.findTrainerById(trainer2.getId());

        Assertions.assertTrue(updatedTrainerById.isPresent());


        verify(trainerService).saveTrainer(trainer);
        verify(trainerService).updateTrainer(trainer2);


        Assertions.assertEquals(updatedTrainerById.get().getUserId(), trainer2.getUserId());
        Assertions.assertEquals(updatedTrainerById.get().getUser(), trainer2.getUser());
        Assertions.assertEquals(updatedTrainerById.get().getId(), trainer2.getId());
        Assertions.assertEquals(updatedTrainerById.get().getSpecialization(), trainer2.getSpecialization());

    }

    @Test
    public void updateTraining_withExistingEntity_updatesEntityDetails() {
        facade.saveTraining(training);

        when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById1 = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById1.isPresent());

        training2.setId(training.getId());

        trainingService.updateTraining(training2);

        verify(trainingService).saveTraining(training);
        verify(trainingService).updateTraining(training2);


        when(trainingService.findTrainingById(training2.getId())).thenReturn(Optional.of(training2));


        Optional<Training> updateTrainingById = trainingService.findTrainingById(training2.getId());

        Assertions.assertTrue(updateTrainingById.isPresent());


        Assertions.assertEquals(training2.getId(), updateTrainingById.get().getId());
        Assertions.assertEquals(training2.getTraineeID(), updateTrainingById.get().getTraineeID());
        Assertions.assertEquals(training2.getTrainerID(), updateTrainingById.get().getTrainerID());
        Assertions.assertEquals(training2.getTrainingName(), updateTrainingById.get().getTrainingName());
        Assertions.assertEquals(training2.getTrainingDate(), updateTrainingById.get().getTrainingDate());
        Assertions.assertEquals(training2.getTrainingType(), updateTrainingById.get().getTrainingType());
        Assertions.assertEquals(training2.getTrainingDuration(), updateTrainingById.get().getTrainingDuration());

    }

    @Test
    public void findTrainingById_withExistingId_returnsEntity() {
        facade.saveTraining(training);

        when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());

        verify(trainingService).saveTraining(training);
        verify(trainingService).findTrainingById(training.getId());

        Assertions.assertEquals(training.getId(), trainingById.get().getId());
        Assertions.assertEquals(training.getTraineeID(), trainingById.get().getTraineeID());
        Assertions.assertEquals(training.getTrainerID(), trainingById.get().getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), trainingById.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), trainingById.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), trainingById.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), trainingById.get().getTrainingDuration());

    }

    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        facade.saveTrainee(trainee);

        when(traineeService.findTraineeById(trainee.getId())).thenReturn(Optional.of(trainee));


        Optional<Trainee> traineeById = traineeService.findTraineeById(trainee.getId());

        Assertions.assertTrue(traineeById.isPresent());

        verify(traineeService).saveTrainee(trainee);
        verify(traineeService).findTraineeById(trainee.getId());

        Assertions.assertEquals(traineeById.get().getId(), trainee.getId());
        Assertions.assertEquals(traineeById.get().getUser(), trainee.getUser());
        Assertions.assertEquals(traineeById.get().getAddress(), trainee.getAddress());
        Assertions.assertEquals(traineeById.get().getDateOfBirth(), trainee.getDateOfBirth());
    }

    @Test
    public void findTrainerById_withExistingId_returnsEntity() {
        facade.saveTrainer(trainer);

        when(trainerService.findTrainerById(trainer.getId())).thenReturn(Optional.of(trainer));


        Optional<Trainer> trainerById = trainerService.findTrainerById(trainer.getId());

        Assertions.assertTrue(trainerById.isPresent());

        verify(trainerService).saveTrainer(trainer);
        verify(trainerService).findTrainerById(trainer.getId());

        Assertions.assertEquals(trainerById.get().getId(), trainer.getId());
        Assertions.assertEquals(trainerById.get().getUser(), trainer.getUser());
        Assertions.assertEquals(trainerById.get().getSpecialization(), trainer.getSpecialization());
    }

    @Test
    public void deleteTraineeById_withValidId_returnsValidEntity() {
        facade.saveTrainee(trainee);

        when(traineeService.findTraineeById(trainee.getId())).thenReturn(Optional.of(trainee));

        traineeService.deleteTraineeById(trainee.getId());

        when(traineeService.findTraineeById(trainee.getId())).thenReturn(Optional.empty());

        verify(traineeService).deleteTraineeById(trainee.getId());

        Assertions.assertEquals(traineeService.findTraineeById(trainee.getId()), Optional.empty());

    }

    @Test
    public void deleteTrainerById_withValidId_returnsValidEntity() {
        facade.saveTrainer(trainer);

        when(trainerService.findTrainerById(trainer.getId())).thenReturn(Optional.of(trainer));

        trainerService.deleteTrainerById(trainer.getId());

        when(trainerService.findTrainerById(trainer.getId())).thenReturn(Optional.empty());

        verify(trainerService).deleteTrainerById(trainer.getId());

        Assertions.assertEquals(trainerService.findTrainerById(trainer.getId()), Optional.empty());

    }

    @Test
    public void deleteTrainingById_withValidId_returnsValidEntity() {
        facade.saveTraining(training);

        when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.of(training));

        trainingService.deleteTrainingById(training.getId());

        when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.empty());

        verify(trainingService).deleteTrainingById(training.getId());


        Assertions.assertEquals(trainingService.findTrainingById(training.getId()), Optional.empty());

    }

    @Test
    public void findAllTrainees_withExistingData_returnsValidEntities() {
        facade.saveTrainee(trainee);
        facade.saveTrainee(trainee2);

        Set<Map.Entry<Long, Trainee>> mockTraineeSet = new HashSet<>();
        mockTraineeSet.add(new AbstractMap.SimpleEntry<>(trainee.getId(), trainee));
        mockTraineeSet.add(new AbstractMap.SimpleEntry<>(trainee2.getId(), trainee2));

        when(traineeService.getAllTrainees()).thenReturn(mockTraineeSet);


        Set<Map.Entry<Long, Trainee>> result = traineeService.getAllTrainees();

        verify(traineeService).getAllTrainees();
        verify(traineeService).saveTrainee(trainee);
        verify(traineeService).saveTrainee(trainee2);

        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainee.getId(), trainee)));
        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainee2.getId(), trainee2)));


    }

    @Test
    public void findAllTrainers_withExistingData_returnsValidEntities() {
        facade.saveTrainer(trainer);
        facade.saveTrainer(trainer2);

        Set<Map.Entry<Long, Trainer>> mockTrainerSet = new HashSet<>();
        mockTrainerSet.add(new AbstractMap.SimpleEntry<>(trainer.getId(), trainer));
        mockTrainerSet.add(new AbstractMap.SimpleEntry<>(trainer2.getId(), trainer2));

        when(trainerService.getAllTrainers()).thenReturn(mockTrainerSet);


        Set<Map.Entry<Long, Trainer>> result = trainerService.getAllTrainers();

        verify(trainerService).getAllTrainers();


        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainer.getId(), trainer)));
        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainer2.getId(), trainer2)));


    }

    @Test
    public void findAllTrainings_withExistingData_returnsValidEntities() {
        facade.saveTraining(training);
        facade.saveTrainer(trainer2);

        Set<Map.Entry<Long, Training>> mockTrainingSet = new HashSet<>();
        mockTrainingSet.add(new AbstractMap.SimpleEntry<>(training.getId(), training));
        mockTrainingSet.add(new AbstractMap.SimpleEntry<>(training2.getId(), training2));

        when(trainingService.getAllTrainings()).thenReturn(mockTrainingSet);


        Set<Map.Entry<Long, Training>> result = trainingService.getAllTrainings();


        verify(trainingService).saveTraining(training);

        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(training.getId(), training)));
        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(training2.getId(), training2)));

    }

    @Test
    public void findTraineeByFirstName_withExistingData_returnsValidEntity() {
        facade.saveTrainee(trainee);

        when(traineeService.findTraineeByFirstName(trainee.getUser().getFirstName())).thenReturn(Optional.of(trainee));


        Optional<Trainee> firstName = traineeService.findTraineeByFirstName(trainee.getUser().getFirstName());

        Assertions.assertTrue(firstName.isPresent());

        verify(traineeService).saveTrainee(trainee);
        verify(traineeService).findTraineeByFirstName(trainee.getUser().getFirstName());

        Assertions.assertEquals(traineeService.findTraineeByFirstName(trainee.getUser().getFirstName()),
                Optional.of(trainee));
    }

    @Test
    public void findTraineeByLastName_withExistingData_returnsValidEntity() {
        facade.saveTrainee(trainee);

        when(traineeService.findByLastName(trainee.getUser().getLastName())).thenReturn(Optional.of(trainee));

        Optional<Trainee> lastName = traineeService.findByLastName(trainee.getUser().getLastName());

        Assertions.assertTrue(lastName.isPresent());

        verify(traineeService).saveTrainee(trainee);
        verify(traineeService).findByLastName(trainee.getUser().getLastName());

        Assertions.assertEquals(traineeService.findByLastName(trainee.getUser().getLastName()),
                Optional.of(trainee));
    }

    @Test
    public void findTrainerByFirstName_withExistingData_returnsValidEntity() {
        facade.saveTrainer(trainer);

        when(trainerService.findByFirstName(trainer.getUser().getFirstName())).thenReturn(Optional.of(trainer));

        Optional<Trainer> firstName = trainerService.findByFirstName(trainer.getUser().getFirstName());

        Assertions.assertTrue(firstName.isPresent());

        verify(trainerService).saveTrainer(trainer);
        verify(trainerService).findByFirstName(trainer.getUser().getFirstName());

        Assertions.assertEquals(trainerService.findByFirstName(trainer.getUser().getFirstName()),
                Optional.of(trainer));
    }

    @Test
    public void findTrainerByLastName_withExistingData_returnsValidEntity() {
        facade.saveTrainer(trainer);

        when(trainerService.findByLastName(trainer.getUser().getLastName())).thenReturn(Optional.of(trainer));

        Optional<Trainer> lastName = trainerService.findByLastName(trainer.getUser().getLastName());

        Assertions.assertTrue(lastName.isPresent());

        verify(trainerService).saveTrainer(trainer);
        verify(trainerService).findByLastName(trainer.getUser().getLastName());

        Assertions.assertEquals(trainerService.findByLastName(trainer.getUser().getLastName()),
                Optional.of(trainer));
    }

    @Test
    public void findByTrainingName_withExistingData_returnsValidEntity() {
        facade.saveTraining(training);

        when(trainingService.findByTrainingName(training.getTrainingName())).thenReturn(Optional.of(training));


        Optional<Training> byTrainingName = trainingService.findByTrainingName(training.getTrainingName());
        Assertions.assertTrue(byTrainingName.isPresent());

        verify(trainingService).saveTraining(training);
        verify(trainingService).findByTrainingName(training.getTrainingName());

        Assertions.assertEquals(training.getId(), byTrainingName.get().getId());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), byTrainingName.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), byTrainingName.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), byTrainingName.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), byTrainingName.get().getTrainingDuration());

    }

    @Test
    public void findByTrainingType_withExistingData_returnsValidEntity() {
        facade.saveTraining(training);

        when(trainingService.findByTrainingType(training.getTrainingType())).thenReturn(Optional.of(training));


        Optional<Training> byTrainingName = trainingService.findByTrainingType(training.getTrainingType());
        Assertions.assertTrue(byTrainingName.isPresent());

        verify(trainingService).saveTraining(training);
        verify(trainingService).findByTrainingType(training.getTrainingType());


        Assertions.assertEquals(training.getId(), byTrainingName.get().getId());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), byTrainingName.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), byTrainingName.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), byTrainingName.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), byTrainingName.get().getTrainingDuration());

    }







}
