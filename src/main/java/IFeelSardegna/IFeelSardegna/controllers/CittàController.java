package IFeelSardegna.IFeelSardegna.controllers;

import IFeelSardegna.IFeelSardegna.entites.Città;
import IFeelSardegna.IFeelSardegna.entites.Mari;
import IFeelSardegna.IFeelSardegna.exceptions.BadRequestException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewCittàDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewMareDTO;
import IFeelSardegna.IFeelSardegna.service.CittàService;
import IFeelSardegna.IFeelSardegna.service.MariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/città")
public class CittàController {
    @Autowired
    private CittàService cittàService;

    @GetMapping("")
    public Page<Città> getCittà(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy){
        return cittàService.getCittà(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Città findById(@PathVariable long id){
        return cittàService.findById(id);
    }

    @PostMapping("")
    public Città saveNewCittà(@RequestBody @Validated NewCittàDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return cittàService.save(body);
        }
    }

    @PutMapping("/{id}")
    public Città findByIdAndUpdate(@PathVariable long id, @RequestBody Città body){
        return cittàService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String findByIdAndDelete(@PathVariable long id) {
        return cittàService.findByIdAndDelete(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadCover(@PathVariable long id, @RequestParam("cover") MultipartFile body) throws IOException {
        return cittàService.uploadCover(id, body);
    }
}
