package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);

    @Autowired
    private TrainerDAOImpl trainerDAOImpl;


    public void saveTrainer(Trainer trainer) throws ServiceException {
        if (trainer.getId() == null || trainer.getId() == 0
                || trainer.getUser().getFirstName() == null
                || trainer.getUser().getFirstName().isEmpty()
                || trainer.getUser().getLastName() == null
                || trainer.getUser().getLastName().isEmpty()
                || trainer.getUser().getPassword() == null
                || trainer.getUser().getPassword().isEmpty()
                || trainer.getUser().getUsername() == null
                || trainer.getUser().getUsername().isEmpty()
                || trainer.getUserId() == null || trainer.getUserId() <= 0
                || trainer.getSpecialization() == null
                || trainer.getSpecialization().isEmpty()) {
            logger.warn("Trainer save failed");
            throw new ServiceException("Trainer save failed, trainer is invalid");
        }
        trainerDAOImpl.save(trainer);
        logger.info("Trainer saved successfully {} ", trainer);

    }

    public void updateTrainer(Trainer trainer) throws ServiceException {
        if (trainer.getId() == null || trainer.getId() == 0
                || trainer.getUser().getFirstName() == null
                || trainer.getUser().getFirstName().isEmpty()
                || trainer.getUser().getLastName() == null
                || trainer.getUser().getLastName().isEmpty()
                || trainer.getUser().getPassword() == null
                || trainer.getUser().getPassword().isEmpty()
                || trainer.getUser().getUsername() == null
                || trainer.getUser().getUsername().isEmpty()
                || trainer.getUserId() == null || trainer.getUserId() <= 0
                || trainer.getSpecialization() == null
                || trainer.getSpecialization().isEmpty()) {
            logger.warn("Trainer update failed");
            throw new ServiceException("Trainer update failed, trainer is invalid");
        }

        trainerDAOImpl.update(trainer);
        logger.info("Trainer updated {} ", trainer);

    }

    public void deleteTrainerById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            logger.warn("Trainer delete by id failed");
            throw new ServiceException("Remove trainee by id failed, id is invalid");
        }

        trainerDAOImpl.deleteById(id);
        logger.info("Trainer id deleted {} ", id);

    }

    public Optional<Trainer> findTrainerById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Trainer find by id failed");
            throw new ServiceException("Trainer find by id failed, id is invalid");
        }
        logger.info("Found trainer by id {} ", id);
        Optional<Trainer> byId = trainerDAOImpl.findById(id);
        return Optional.of(byId.orElseThrow());
    }

    public Set<Map.Entry<Long, Trainer>> getAllTrainers() {
        return trainerDAOImpl.findAll();
    }

    public  Optional<Trainer> findByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            logger.warn("Trainer findByFirstName failed");
            throw new ServiceException("Trainee find by firstName failed, firstName is invalid");
        }
        logger.info("Found trainer by name {} ", firstName);
        Optional<Trainer> byFirstName = trainerDAOImpl.findByFirstName(firstName);
        return Optional.of(byFirstName.orElseThrow());
    }

    public Optional<Trainer> findByLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            logger.warn("Trainer findByLastName failed");
            throw new ServiceException("Trainee findByLastName failed, lastName is invalid");
        }
        logger.info("Found trainer by lastName {} ", lastName);
        Optional<Trainer> byLastName = trainerDAOImpl.findByLastName(lastName);
        return Optional.of(byLastName.orElseThrow());
    }

    public Optional<Trainer> findBySpecialization(String specialization) {
        if (specialization == null || specialization.isEmpty()) {
            logger.warn("Trainer findBySpecialization failed");
            throw new ServiceException("Trainee findBySpecialization failed, specialization is invalid");
        }
        logger.info("Found trainer by specialization {} ", specialization);
        Optional<Trainer> bySpecialization = trainerDAOImpl.findBySpecialization(specialization);
        return Optional.of(bySpecialization.orElseThrow());
    }
}
