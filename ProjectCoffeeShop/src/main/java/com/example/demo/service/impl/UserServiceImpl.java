package com.example.demo.service.impl;

import com.example.demo.models.entity.RoleEntity;
import com.example.demo.models.entity.UserEntity;
import com.example.demo.models.entity.enums.RoleEntityNameEnum;
import com.example.demo.models.service.UserServiceModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.web.exeptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserServiceModel userServiceModel) {
        RoleEntity userRole = roleRepository.findByRole(RoleEntityNameEnum.USER);
        System.out.println();
        UserEntity user = modelMapper.map(userServiceModel,UserEntity.class);
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        user.setPicUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.subpng.com%2Fpng-9lw7jx%2F&psig=AOvVaw0c6sFiecNtCHsdCz5bBcVU&ust=1636762391519000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCKCY_pvFkfQCFQAAAAAdAAAAABAD");
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
       return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void initializeUsers() {
        if (userRepository.count() == 0) {
            roleService.initializeRoles();
            RoleEntity adminRole = roleRepository.findByRole(RoleEntityNameEnum.ADMIN);
            RoleEntity moderatorRole = roleRepository.findByRole(RoleEntityNameEnum.MODERATOR);
            RoleEntity userRole = roleRepository.findByRole(RoleEntityNameEnum.USER);

            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setFullName("Ivailo Netzov");
            admin.setPicUrl("https://lh3.googleusercontent.com/proxy/Nk8iU0OPG9yZ3i_ayl5X4OBIudpt6PbCuIWd5LsR6fHXHAS5u920O07vBNCroMY4cnsBNKGMkjnlqBJSCV8");
            admin.setEmail("admin@Gmail.com");
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);

            UserEntity moderator = new UserEntity();
            moderator.setUsername("moderator");
            moderator.setPassword(passwordEncoder.encode("12345"));
            moderator.setFullName("Liliq Kerezova");
            moderator.setPicUrl("https://lh3.googleusercontent.com/proxy/Nk8iU0OPG9yZ3i_ayl5X4OBIudpt6PbCuIWd5LsR6fHXHAS5u920O07vBNCroMY4cnsBNKGMkjnlqBJSCV8");
            moderator.setEmail("moderator@Gmail.com");
            moderator.setRoles(Set.of(moderatorRole));
            userRepository.save(moderator);

            UserEntity userAcc = new UserEntity();
            userAcc.setUsername("userAcc");
            userAcc.setPassword(passwordEncoder.encode("12345"));
            userAcc.setFullName("Vesselin Netzov");
            userAcc.setPicUrl("https://lh3.googleusercontent.com/proxy/Nk8iU0OPG9yZ3i_ayl5X4OBIudpt6PbCuIWd5LsR6fHXHAS5u920O07vBNCroMY4cnsBNKGMkjnlqBJSCV8");
            userAcc.setEmail("user@Gmail.com");
            userAcc.setRoles(Set.of(userRole));
            userRepository.save(userAcc);


        }
    }

    @Transactional
    @Override
    public Optional<UserEntity> update(long userId, String fullName) {
        Optional<UserEntity> user = userRepository.findById(userId);
        return userRepository.findById(userId).map(target -> {
            target.setFullName(fullName);
            return target;
        });
    }
}
