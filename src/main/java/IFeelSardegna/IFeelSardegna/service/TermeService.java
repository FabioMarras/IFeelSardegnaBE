package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.Terme;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewTermaDTO;
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
public class TermeService {

    @Autowired
    private TermeRepository termeRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Terme findById(long id){
        return termeRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    public Page<Terme> getTerme(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return termeRepository.findAll(pageable);
    }

    public Terme save(@RequestBody NewTermaDTO body)  {
        Terme NewTerme = new Terme();
        NewTerme.setName(body.name());
        NewTerme.setCover(body.cover());
        NewTerme.setText(body.text());
        Terme saveTerme = termeRepository.save(NewTerme);
        return saveTerme;
    }
    public Terme findByIdAndUpdate(Long id, Terme body) {
        Terme terme = termeRepository.findById(id).get();
        terme.setName(body.getName());
        terme.setCover(body.getCover());
        terme.setText(body.getText());
        return termeRepository.save(terme);
    }

    public String findByIdAndDelete(Long id) {
        Terme terme = this.findById(id);
        termeRepository.delete(terme);
        return "La terma con id: " + id + " è stata eliminata con successo!!";
    }

    public String uploadCover(Long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Terme terme = termeRepository.findById(id).orElse(null);
            if (terme != null) {
                terme.setCover(imageUrl);
                termeRepository.save(terme);
            }

            return imageUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Impossibile caricare l'immagine", ex);
        }
    }
}
