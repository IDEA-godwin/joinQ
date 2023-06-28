package com.example.joinq.serviceImpl;

import com.example.joinq.config.security.SecurityUtils;
import com.example.joinq.domain.dto.OrganizationDto;
import com.example.joinq.domain.dto.UserDto;
import com.example.joinq.domain.entities.Organization;
import com.example.joinq.domain.entities.User;
import com.example.joinq.repository.OrganizationRepository;
import com.example.joinq.repository.UserRepository;
import com.example.joinq.service.UserAdministrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserAdministrationServiceImpl implements UserAdministrationService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder encoder;

    @Override
    public User registerUser(User user) {

        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "user with username" + user.getUsername() + "exist, if this is  you try logging in");
        }
        user.setId(UUID.randomUUID());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    @Override
    public User getUserAccountByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "user with username" + username + "not found");
        }
        return user.get();
    }

    @Override
    public UserDto
    getUserAccount() {
        return SecurityUtils.getCurrentUserEmail()
                .map(username -> {
                    User user = getUserAccountByUsername(username);
                    Optional<Organization> organization = organizationRepository.findOrganizationByUser(user);
                    UserDto res = new UserDto();
                    res.setEmail(user.getEmail());
                    res.setUsername(user.getUsername());
                    res.setContactNumber(user.getContactNumber());
                    res.setOrganizationAccount(organization.isPresent());
                    res.setOrganization(organization.orElse(null));
                    return res;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not accessible"));
    }

    @Override
    public Organization createOrganizationAccount(Organization organization) {
        organization.setUser(registerUser(organization.getUser()));
        organization.setCreatedAt(new Date());
        return organizationRepository.save(organization);
    }

    @Override
    public OrganizationDto getOrganizationAccount(String adminUsername, String password) {
        return null;
    }
}
