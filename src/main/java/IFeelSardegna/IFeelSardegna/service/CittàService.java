package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.Città;
import IFeelSardegna.IFeelSardegna.entites.Mari;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewCittàDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewMareDTO;
import IFeelSardegna.IFeelSardegna.repository.CittàRepository;
import IFeelSardegna.IFeelSardegna.repository.MariRepository;
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
public class CittàService {

    @Autowired
    private CittàRepository cittàRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Città findById(long id){
        return cittàRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    public Page<Città> getCittà(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return cittàRepository.findAll(pageable);
    }

    public Città save(@RequestBody NewCittàDTO body)  {
        Città NewCittà = new Città();
        NewCittà.setName(body.name());
        NewCittà.setCover(body.cover());
        NewCittà.setText(body.text());
        Città saveCittà = cittàRepository.save(NewCittà);
        return saveCittà;
    }
    public Città findByIdAndUpdate(Long id, Città body) {
        Città città = cittàRepository.findById(id).get();
        città.setName(body.getName());
        città.setCover(body.getCover());
        città.setText(body.getText());
        return cittàRepository.save(città);
    }

    public String findByIdAndDelete(Long id) {
        Città città = this.findById(id);
        cittàRepository.delete(città);
        return "La città con id: " + id + " è stata eliminata con successo!!";
    }

    public String uploadCover(Long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Città città  = cittàRepository.findById(id).orElse(null);
            if (città != null) {
                città.setCover(imageUrl);
                cittàRepository.save(città);
            }

            return imageUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Impossibile caricare l'immagine", ex);
        }
    }
}
