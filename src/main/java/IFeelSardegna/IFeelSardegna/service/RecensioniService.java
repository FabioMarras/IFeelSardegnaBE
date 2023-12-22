package IFeelSardegna.IFeelSardegna.service;

import IFeelSardegna.IFeelSardegna.entites.Città;
import IFeelSardegna.IFeelSardegna.entites.Mari;
import IFeelSardegna.IFeelSardegna.entites.Recensioni;
import IFeelSardegna.IFeelSardegna.exceptions.NotFoundException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewCittàDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewRecensioneDTO;
import IFeelSardegna.IFeelSardegna.repository.CittàRepository;
import IFeelSardegna.IFeelSardegna.repository.MariRepository;
import IFeelSardegna.IFeelSardegna.repository.RecensioniRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class RecensioniService {


    @Autowired
    private RecensioniRepository recensioniRepository;

    @Autowired
    private CittàRepository cittàRepository;

    @Autowired
    private MariRepository mariRepository;

    public Recensioni findById(long id){
        return recensioniRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    public Page<Recensioni> getRecensioni(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return recensioniRepository.findAll(pageable);
    }

    public Recensioni save(@RequestBody NewRecensioneDTO body, @PathVariable int id)  {
        Recensioni NewRecensione = new Recensioni();
        NewRecensione.setTesto(body.testo());

        Città città = cittàRepository.findById((long) id).orElseThrow(() -> new NotFoundException("Città non trovata con ID: " + id));
        NewRecensione.setCitta(città);

        Recensioni saveRecensioni = recensioniRepository.save(NewRecensione);
        return saveRecensioni;
    }
    public Recensioni saveMare(@RequestBody NewRecensioneDTO body, @PathVariable int id)  {
        Recensioni NewRecensione = new Recensioni();
        NewRecensione.setTesto(body.testo());

        Mari mari = mariRepository.findById((long) id).orElseThrow(() -> new NotFoundException("Mare non trovato con ID: " + id));
        NewRecensione.setMari(mari);

        Recensioni saveRecensioni = recensioniRepository.save(NewRecensione);
        return saveRecensioni;
    }
    public Recensioni findByIdAndUpdate(Long id, Recensioni body) {
        Recensioni recensioni = recensioniRepository.findById(id).get();
        recensioni.setTesto(body.getTesto());
        recensioni.setCitta(body.getCitta());
        return recensioniRepository.save(recensioni);
    }

    public String findByIdAndDelete(Long id) {
        Recensioni recensioni = this.findById(id);
        recensioniRepository.delete(recensioni);
        return "La recensione con id: " + id + " è stato eliminato con successo!!";
    }


}
