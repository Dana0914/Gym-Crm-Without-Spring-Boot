package epam.com.gymapplication.dao;


import epam.com.gymapplication.entity.Trainee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;



@Repository
public interface TraineeRepository extends CrudRepository<Trainee, Long> {



    @Query(value = "select t from Trainee t where t.user.firstname = :firstname", nativeQuery = false)
    Optional<Trainee> findByFirstName(@Param("firstname") String firstName);

    @Query(value = "select t from Trainee t where t.user.lastname = :lastname", nativeQuery = false)
    Optional<Trainee> findByLastName(@Param("lastname") String lastName);

    @Query(value = "select t from Trainee t where t.user.username = :username", nativeQuery = false)
    Optional<Trainee> findByUsername(@Param("username") String username);

}
