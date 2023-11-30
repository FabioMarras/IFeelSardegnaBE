package IFeelSardegna.IFeelSardegna.controllers;

import IFeelSardegna.IFeelSardegna.entites.Capoluoghi;
import IFeelSardegna.IFeelSardegna.entites.Province;
import IFeelSardegna.IFeelSardegna.exceptions.BadRequestException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewCapoluogoDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewProvinciaDTO;
import IFeelSardegna.IFeelSardegna.service.CapoluoghiService;
import IFeelSardegna.IFeelSardegna.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/capoluoghi")
public class CapoluoghiController {

    @Autowired
    private CapoluoghiService capoluoghiService;

    @GetMapping("")
    public Page<Capoluoghi> getCapoluoghi(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String orderBy){
        return capoluoghiService.getCapoluoghi(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Capoluoghi findById(@PathVariable long id){
        return capoluoghiService.findById(id);
    }

    @PostMapping("")
    public Capoluoghi saveNewProvincia(@RequestBody @Validated NewCapoluogoDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return capoluoghiService.save(body);
        }
    }

    @PutMapping("/{id}")
    public Capoluoghi findByIdAndUpdate(@PathVariable long id, @RequestBody Capoluoghi body){
        return capoluoghiService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String findByIdAndDelete(@PathVariable long id) {
        return capoluoghiService.findByIdAndDelete(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadCover(@PathVariable long id, @RequestParam("cover") MultipartFile body) throws IOException {
        return capoluoghiService.uploadCover(id, body);
    }
}
