package me.projects.SelfOKRs.repositories;

import me.projects.SelfOKRs.entities.Objective;
import me.projects.SelfOKRs.entities.UserEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ObjectiveRepositoryTest {

    @Autowired
    ObjectiveRepository objectiveRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    Objective objective1;
    Objective objective2;

    @BeforeAll
    public void setUp() {
        UserEntity user = new UserEntity("Testing User", "test@mail.com", "pass");
        userEntityRepository.save(user);
        objective1 = new Objective("Testing objective 1",1, user);
        objective2 = new Objective("Testing objective 2",5, user);
        objectiveRepository.save(objective1);
        objectiveRepository.save(objective2);

    }
    @AfterAll
    public void tearDown() {
        objectiveRepository.deleteAll();
    }

    // Test Create operation
    @Test
    public void shouldSaveObjective() {
        Objective objective = new Objective("Testing objective 3",3, null);
        Objective savedObjective = objectiveRepository.save(objective);
        assertEquals(savedObjective, objective);
    }

    // Test Read operation for All
    @Test
    public void shouldReturnListOfAllObjectives() {
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertEquals(objective1, objectiveList.get(0));
        assertEquals(objective2, objectiveList.get(1));
    }

    // Test Update operation
    // Test Delete operation

}
