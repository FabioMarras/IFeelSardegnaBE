package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.User;
import IFeelSardegna.IFeelSardegna.enums.Role;
import IFeelSardegna.IFeelSardegna.exceptions.UnauthorizedException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewUserDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.UserLoginDTO;
import IFeelSardegna.IFeelSardegna.repository.UserRepository;
import IFeelSardegna.IFeelSardegna.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Arrays;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public String[] authenticateUser(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return new String[]{jwtTools.createToken(user), user.getName()};
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }
    }

    public User registerUser(NewUserDTO body) throws IOException {

        // verifico se l'email è già utilizzata


        User newUser = new User();
        newUser.setUsername(body.username());
        newUser.setName(body.name());
        newUser.setLastName(body.lastName());
        newUser.setEmail(body.email());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setRole(Role.USER);
        userRepository.save(newUser);

        return newUser;
    }
}
