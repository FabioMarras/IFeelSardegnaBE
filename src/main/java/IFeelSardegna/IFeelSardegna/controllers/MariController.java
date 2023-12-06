package IFeelSardegna.IFeelSardegna.controllers;

import IFeelSardegna.IFeelSardegna.entites.Mari;
import IFeelSardegna.IFeelSardegna.entites.Terme;
import IFeelSardegna.IFeelSardegna.exceptions.BadRequestException;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewMareDTO;
import IFeelSardegna.IFeelSardegna.payloads.entities.NewTermaDTO;
import IFeelSardegna.IFeelSardegna.service.MariService;
import IFeelSardegna.IFeelSardegna.service.TermeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/mari")
public class MariController {
    @Autowired
    private MariService mariService;

    @GetMapping("")
    public Page<Mari> getMari(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy){
        return mariService.getMari(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Mari findById(@PathVariable long id){
        return mariService.findById(id);
    }

    @PostMapping("")
    public Mari saveNewMari(@RequestBody @Validated NewMareDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return mariService.save(body);
        }
    }

    @PutMapping("/{id}")
    public Mari findByIdAndUpdate(@PathVariable long id, @RequestBody Mari body){
        return mariService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String findByIdAndDelete(@PathVariable long id) {
        return mariService.findByIdAndDelete(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadCover(@PathVariable long id, @RequestParam("cover") MultipartFile body) throws IOException {
        return mariService.uploadCover(id, body);
    }
}
