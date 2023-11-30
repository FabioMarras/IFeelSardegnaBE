package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.User;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

@Autowired
private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    public User findById(long id){
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(email));
    }
    public Page<User> getUsers(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }
    public User findByIdAndUpdate(Long id, User body) throws NotFoundException {
        User user = userRepository.findById(id).get();
        user.setUsername(body.getUsername());
        user.setName(body.getName());
        user.setLastName(body.getLastName());
        user.setEmail(body.getEmail());
        user.setAvatar(body.getAvatar());
        return userRepository.save(user);
    }
    public String findByIdAndDelete(Long id) {
        User user = this.findById(id);
        userRepository.delete(user);
        return "User con id " + id + " è stato eliminato con successo!!";
    }

    public String uploadAvatar(Long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                user.setAvatar(imageUrl);
                userRepository.save(user);
            }

            return imageUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Impossibile caricare l'immagine", ex);
        }
    }
}
