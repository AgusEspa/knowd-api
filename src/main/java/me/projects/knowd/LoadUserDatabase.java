package me.projects.knowd;

import me.projects.knowd.repositories.SubjectRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class LoadUserDatabase implements CommandLineRunner {

    private final UserEntityRepository userRepository;

    private final SubjectRepository subjectRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public LoadUserDatabase(UserEntityRepository userRepository, SubjectRepository subjectRepository) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
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
