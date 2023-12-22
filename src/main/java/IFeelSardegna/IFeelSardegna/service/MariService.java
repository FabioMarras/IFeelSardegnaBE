package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.Mari;
import IFeelSardegna.IFeelSardegna.entites.Terme;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewMareDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewTermaDTO;
import IFeelSardegna.IFeelSardegna.repository.MariRepository;
import IFeelSardegna.IFeelSardegna.repository.TermeRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class MariService {

    @Autowired
    private MariRepository mariRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Mari findById(long id){
        return mariRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    public Page<Mari> getMari(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return mariRepository.findAll(pageable);
    }

    public Mari save(@RequestBody NewMareDTO body)  {
        Mari NewMari = new Mari();
        NewMari.setName(body.name());
        NewMari.setCover(body.cover());
        NewMari.setText(body.text());
        NewMari.setSpiaggiaDetails(body.spiaggiaDetails());
        NewMari.setMariDetails(body.mariDetails());
        Mari saveMari = mariRepository.save(NewMari);
        return saveMari;
    }
    public Mari findByIdAndUpdate(Long id, NewMareDTO body) {
        Mari mari = mariRepository.findById(id).get();
        mari.setName(body.name());
        mari.setCover(body.cover());
        mari.setText(body.text());
        mari.setSpiaggiaDetails(body.spiaggiaDetails());
        mari.setMariDetails(body.mariDetails());
        return mariRepository.save(mari);
    }

    public String findByIdAndDelete(Long id) {
        Mari mari = this.findById(id);
        mariRepository.delete(mari);
        return "Il mare con id: " + id + " è stato eliminato con successo!!";
    }

    public String uploadCover(Long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Mari mari = mariRepository.findById(id).orElse(null);
            if (mari != null) {
                mari.setCover(imageUrl);
                mariRepository.save(mari);
            }

            return imageUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Impossibile caricare l'immagine", ex);
        }
    }
}
