package epam.com.gymapplication.controller;


import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(value = "/register" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<TrainerDTO> registerTrainer(@RequestBody TrainerDTO trainerRequestDTO) {

        TrainerDTO registeredTrainerRequest = trainerService.createTrainerProfile(trainerRequestDTO);
        return new ResponseEntity<>(registeredTrainerRequest, HttpStatus.CREATED);


    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {

        boolean authenticatedTrainerProfile = trainerService.authenticateTrainerProfile(username, password);
        if (authenticatedTrainerProfile) {
            return ResponseEntity.ok("Authorized successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PutMapping("/change-login")
    public ResponseEntity<String> changeLogin(@RequestParam("username") String username,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldPassword") String oldPassword) {
        boolean passwordChange = trainerService.changePassword(username, password, oldPassword);
        if (passwordChange) {
            return ResponseEntity.ok("Password changed successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @GetMapping(value = "/trainer-profile")
    public ResponseEntity<TrainerDTO> getTrainerProfile(@RequestParam("username") String username) {
        TrainerDTO trainerProfileByUsername = trainerService.findTrainerProfileByUsername(username);
        return new ResponseEntity<>(trainerProfileByUsername, HttpStatus.OK);
    }

    @PutMapping(value = "/trainer-profile/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainerDTO> updateTrainerProfile(@PathVariable("id") Long id,
                                                           @RequestBody TrainerDTO trainerDTO)
    {

        TrainerDTO updateTrainerProfile = trainerService.updateTrainerProfile(id, trainerDTO);
        return new ResponseEntity<>(updateTrainerProfile, HttpStatus.OK);


    }
    @GetMapping(value = "not-assigned")
    public ResponseEntity<List<TrainerDTO>> getUnassignedOnTraineeTrainersList(@RequestParam("username") String username)
    {

        List<TrainerDTO> unassignedTraineesTrainersList = trainerService.findUnassignedTraineesTrainersListByTraineeUsername(username);
        return new ResponseEntity<>(unassignedTraineesTrainersList, HttpStatus.OK);

    }

    @PatchMapping(value = "/active-inactive")
    public ResponseEntity<Void> activateDeactivateTrainer(@RequestParam("username") String username,
                                                          @RequestBody TrainerDTO trainerDTO) {

        trainerService.activateOrDeactivateTrainerStatus(username, trainerDTO.getActive());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteTrainerProfile(@RequestParam("username") String username) {
        trainerService.deleteTrainerProfileByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }









}
