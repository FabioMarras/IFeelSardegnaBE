package IFeelSardegna.IFeelSardegna.controllers;

import IFeelSardegna.IFeelSardegna.entites.Province;
import IFeelSardegna.IFeelSardegna.entites.User;
import IFeelSardegna.IFeelSardegna.exceptions.BadRequestException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewProvinciaDTO;
import IFeelSardegna.IFeelSardegna.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/province")
public class ProvincieController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("")
    public Page<Province> getProvince(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String orderBy){
        return provinceService.getProvince(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Province findById(@PathVariable long id){
        return provinceService.findById(id);
    }

    @PostMapping("")
    public Province saveNewProvincia(@RequestBody @Validated NewProvinciaDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return provinceService.save(body);
        }
    }

    @PutMapping("/{id}")
    public Province findByIdAndUpdate(@PathVariable long id, @RequestBody Province body){
        return provinceService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String findByIdAndDelete(@PathVariable long id) {
        return provinceService.findByIdAndDelete(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadCover(@PathVariable long id, @RequestParam("cover") MultipartFile body) throws IOException {
        return provinceService.uploadCover(id, body);
    }
}
