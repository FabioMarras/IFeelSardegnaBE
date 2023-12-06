package IFeelSardegna.IFeelSardegna.controllers;

import IFeelSardegna.IFeelSardegna.entites.Province;
import IFeelSardegna.IFeelSardegna.entites.Terme;
import IFeelSardegna.IFeelSardegna.exceptions.BadRequestException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewProvinciaDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewTermaDTO;
import IFeelSardegna.IFeelSardegna.service.ProvinceService;
import IFeelSardegna.IFeelSardegna.service.TermeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/terme")
public class TermeController {
    @Autowired
    private TermeService termeService;

    @GetMapping("")
    public Page<Terme> getTerme(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String orderBy){
        return termeService.getTerme(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Terme findById(@PathVariable long id){
        return termeService.findById(id);
    }

    @PostMapping("")
    public Terme saveNewTerma(@RequestBody @Validated NewTermaDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return termeService.save(body);
        }
    }

    @PutMapping("/{id}")
    public Terme findByIdAndUpdate(@PathVariable long id, @RequestBody Terme body){
        return termeService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String findByIdAndDelete(@PathVariable long id) {
        return termeService.findByIdAndDelete(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadCover(@PathVariable long id, @RequestParam("cover") MultipartFile body) throws IOException {
        return termeService.uploadCover(id, body);
    }
}
