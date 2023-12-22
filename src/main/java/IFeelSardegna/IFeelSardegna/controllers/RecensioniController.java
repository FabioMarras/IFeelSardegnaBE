package IFeelSardegna.IFeelSardegna.controllers;

import IFeelSardegna.IFeelSardegna.entites.Città;
import IFeelSardegna.IFeelSardegna.entites.Recensioni;
import IFeelSardegna.IFeelSardegna.exceptions.BadRequestException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewCittàDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewRecensioneDTO;
import IFeelSardegna.IFeelSardegna.service.CittàService;
import IFeelSardegna.IFeelSardegna.service.RecensioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/recensioni")
public class RecensioniController {
    @Autowired
    private RecensioniService recensioniService;

    @GetMapping("")
    public Page<Recensioni> getRecensioni(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String orderBy){
        return recensioniService.getRecensioni(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Recensioni findById(@PathVariable long id){
        return recensioniService.findById(id);
    }

    @PostMapping("città/{id}")
    public Recensioni saveNewRecensione(@RequestBody @Validated NewRecensioneDTO body,@PathVariable int id, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return recensioniService.save(body, id);
        }
    }
    @PostMapping("mari/{id}")
    public Recensioni saveNewRecensioneMare(@RequestBody @Validated NewRecensioneDTO body,@PathVariable int id, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return recensioniService.saveMare(body, id);
        }
    }

    @PutMapping("/{id}")
    public Recensioni findByIdAndUpdate(@PathVariable long id, @RequestBody Recensioni body){
        return recensioniService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String findByIdAndDelete(@PathVariable long id) {
        return recensioniService.findByIdAndDelete(id);
    }

}
