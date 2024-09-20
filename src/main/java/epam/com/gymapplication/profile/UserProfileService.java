package epam.com.gymapplication.profile;



import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserProfileService {

    private static int counter = 1;
    private static int serialNumber;

    @Autowired
    private UserRepository userRepository;



    public String concatenateUsername(String firstName, String lastName) {
        String firstLetterToUpperCase = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastLetterToUpperCase = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String username = firstLetterToUpperCase + "." + lastLetterToUpperCase;
        String baseName = username;
        String duplicateUsername = handleDuplicateUsername(baseName);
        return duplicateUsername;
    }

    private String handleDuplicateUsername(String username) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                username += serialNumber;
                counter++;
            }
        }

        serialNumber = counter;
        return username;

    }

}
