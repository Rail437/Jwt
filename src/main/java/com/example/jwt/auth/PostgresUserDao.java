package com.example.jwt.auth;

import com.example.jwt.model.PermissionEntity;
import com.example.jwt.model.RoleEntity;
import com.example.jwt.model.UserEntity;
import com.example.jwt.repository.PermissionRepo;
import com.example.jwt.repository.RoleRepository;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostgresUserDao implements ApplicationUserDao {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepo permissionRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectUserFromDbByUserName(String username) {
        return userRepository.findByUsername(username).map(ApplicationUser::valueOf);
    }

    /**
     * Этот метод я сделал просто для инициализации первых пользователей для теста.
     */
    @Autowired
    private void init(){
        Optional<UserEntity> oliver = userRepository.findByUsername("oliver");
        Optional<UserEntity> henry = userRepository.findByUsername("henry");
        Optional<UserEntity> emma = userRepository.findByUsername("emma");

        if(oliver.isEmpty() & henry.isEmpty() & emma.isEmpty()) {

            PermissionEntity EMPLOYEE_READ = new PermissionEntity("EMPLOYEE_READ");
            PermissionEntity EMPLOYEE_WRITE = new PermissionEntity("EMPLOYEE_WRITE");
            PermissionEntity TASK_READ = new PermissionEntity("TASK_READ");
            PermissionEntity TASK_WRITE = new PermissionEntity("TASK_WRITE");

            RoleEntity EMPLOYEE = new RoleEntity("EMPLOYEE", Set.of(EMPLOYEE_READ, TASK_READ, TASK_WRITE));
            RoleEntity MANAGER = new RoleEntity("MANAGER", Set.of(EMPLOYEE_READ, EMPLOYEE_WRITE, TASK_READ, TASK_WRITE));
            RoleEntity TRAINEE = new RoleEntity("TRAINEE", Set.of(EMPLOYEE_READ, TASK_READ));

            RoleEntity SCRUM_MUSTER = new RoleEntity("SCRUM_MUSTER", Set.of(EMPLOYEE_READ,TASK_READ,TASK_WRITE));

            UserEntity newOliver = new UserEntity(
                    "oliver",
                    passwordEncoder.encode("password"),
                    true,
                    true,
                    true,
                    true,
                    Set.of(EMPLOYEE)
            );

            UserEntity newHenry = new UserEntity(
                    "henry",
                    passwordEncoder.encode("password123"),
                    true,
                    true,
                    true,
                    true,
                    Set.of(MANAGER)
            );

            UserEntity newEmma = new UserEntity(
                    "emma",
                    passwordEncoder.encode("password123"),
                    true,
                    true,
                    true,
                    true,
                    Set.of(TRAINEE)
            );

            UserEntity ugvei = new UserEntity(
                    "ugvei",
                    passwordEncoder.encode("pass"),
                    true,
                    true,
                    true,
                    true,
                    Set.of(SCRUM_MUSTER)
            );
            permissionRepo.save(EMPLOYEE_READ);
            permissionRepo.save(EMPLOYEE_WRITE);
            permissionRepo.save(TASK_READ);
            permissionRepo.save(TASK_WRITE);

            roleRepository.save(EMPLOYEE);
            roleRepository.save(MANAGER);
            roleRepository.save(TRAINEE);
            roleRepository.save(SCRUM_MUSTER);

            userRepository.save(newOliver);
            userRepository.save(newHenry);
            userRepository.save(newEmma);
            userRepository.save(ugvei);
        }
    }
}
