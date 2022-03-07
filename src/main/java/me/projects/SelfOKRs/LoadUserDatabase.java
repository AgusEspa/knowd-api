package me.projects.SelfOKRs;

import me.projects.SelfOKRs.repositories.ObjectiveRepository;
import me.projects.SelfOKRs.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class LoadUserDatabase implements CommandLineRunner {

    private final UserEntityRepository userRepository;

    private final ObjectiveRepository objectiveRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public LoadUserDatabase(UserEntityRepository userRepository, ObjectiveRepository objectiveRepository) {
        this.userRepository = userRepository;
        this.objectiveRepository = objectiveRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
//        RegistrationForm registrationForm = new RegistrationForm("Admin", "admin@mail.com", "5345jg84hgsdfg=M");
//        UserEntity user = registrationForm.toUser();
//        userRepository.save(user);

//        UserEntity user = new UserEntity("Admin", "admin@mail.com", "5345jg84hgsdfg=M");
//        Goal newGoal = new Goal("Be fluent in Swedish", 5, 50, user);
//        goalRepository.save(newGoal);
    }

}
