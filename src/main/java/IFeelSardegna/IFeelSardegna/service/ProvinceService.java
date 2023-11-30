package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.Province;
import IFeelSardegna.IFeelSardegna.entites.User;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewProvinciaDTO;
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
public class ProvinceService {

    @Autowired
    private PronviceRepository pronviceRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Province findById(long id){
        return pronviceRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    public Page<Province> getProvince(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return pronviceRepository.findAll(pageable);
    }

    public Province save(@RequestBody NewProvinciaDTO body)  {
        Province Newprovincia = new Province();
        Newprovincia.setName(body.name());
        Newprovincia.setCover(body.cover());
        Newprovincia.setText(body.text());
        Newprovincia.setIntroText(body.introText());
        Province saveProvincia = pronviceRepository.save(Newprovincia);
        return saveProvincia;
    }

    public Province findByIdAndUpdate(Long id, Province body) {
        Province provincia = pronviceRepository.findById(id).get();
        provincia.setName(body.getName());
        provincia.setCover(body.getCover());
        provincia.setText(body.getText());
        provincia.setIntroText(body.getIntroText());
        return pronviceRepository.save(provincia);
    }

    public String findByIdAndDelete(Long id) {
        Province provincia = this.findById(id);
        pronviceRepository.delete(provincia);
        return "La provincia con id " + id + " è stato eliminata con successo!!";
    }

    public String uploadCover(Long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Province provincia = pronviceRepository.findById(id).orElse(null);
            if (provincia != null) {
                provincia.setCover(imageUrl);
                pronviceRepository.save(provincia);
            }

            return imageUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Impossibile caricare l'immagine", ex);
        }
    }

}
