package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.User;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

@Autowired
private UserRepository userRepository;

    public User findById(long id) throws NotFoundException {
        return (User) userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
