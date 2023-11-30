package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.Capoluoghi;
import IFeelSardegna.IFeelSardegna.entites.Province;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewCapoluogoDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewProvinciaDTO;
import IFeelSardegna.IFeelSardegna.repository.CapoluoghiRepository;
import IFeelSardegna.IFeelSardegna.repository.PronviceRepository;
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
public class CapoluoghiService {

    @Autowired
    private CapoluoghiRepository capoluoghiRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Capoluoghi findById(long id){
        return capoluoghiRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    public Page<Capoluoghi> getCapoluoghi(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return capoluoghiRepository.findAll(pageable);
    }

    public Capoluoghi save(@RequestBody NewCapoluogoDTO body)  {
        Capoluoghi NewCapoluogo = new Capoluoghi();
        NewCapoluogo.setName(body.name());
        NewCapoluogo.setCover(body.cover());
        NewCapoluogo.setText(body.text());
        NewCapoluogo.setIntroText(body.introText());
        Capoluoghi saveCapoluoghi = capoluoghiRepository.save(NewCapoluogo);
        return saveCapoluoghi;
    }

    public Capoluoghi findByIdAndUpdate(Long id, Capoluoghi body) {
        Capoluoghi Capoluogo = capoluoghiRepository.findById(id).get();
        Capoluogo.setName(body.getName());
        Capoluogo.setCover(body.getCover());
        Capoluogo.setText(body.getText());
        Capoluogo.setIntroText(body.getIntroText());
        return capoluoghiRepository.save(Capoluogo);
    }

    public String findByIdAndDelete(Long id) {
        Capoluoghi Capoluogo = this.findById(id);
        capoluoghiRepository.delete(Capoluogo);
        return "Il capoluogo con id " + id + " è stato eliminato con successo!!";
    }

    public String uploadCover(Long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Capoluoghi Capoluogo = capoluoghiRepository.findById(id).orElse(null);
            if (Capoluogo != null) {
                Capoluogo.setCover(imageUrl);
                capoluoghiRepository.save(Capoluogo);
            }

            return imageUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Impossibile caricare l'immagine", ex);
        }
    }

}
