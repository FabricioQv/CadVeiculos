package web2.ofertaveiculos.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;


import jakarta.validation.Valid;
import web2.ofertaveiculos.dto.VeiculosRecordDto;
import web2.ofertaveiculos.model.CategoriasModel;
import web2.ofertaveiculos.model.VeiculosModel;
import web2.ofertaveiculos.repositories.CategoriasRepository;
import web2.ofertaveiculos.repositories.VeiculosRepository;

@Controller
@RequestMapping("/veiculos")
public class VeiculosController2 {

    @Autowired
    VeiculosRepository veiculosRepositorio;

    @Autowired 
    CategoriasRepository categoriasRepository;

    @Autowired
    CategoriasController categoriasController;

    @GetMapping("/inserirVeiculos")
    public ModelAndView formularioVeiculos() {
        ModelAndView mv = new ModelAndView("veiculos/inserirVeiculos");
        ResponseEntity<List<CategoriasModel>> response = categoriasController.listarTodasCategorias();
        List<CategoriasModel> categorias = response.getBody();
        mv.addObject("categorias", categorias);
        return mv;
    }

    @PostMapping("/inserirVeiculos")
public String salvarVeiculo(@ModelAttribute @Valid VeiculosRecordDto veiculosDTO, BindingResult result,
                            @RequestParam("file") MultipartFile imagem, RedirectAttributes msg) {
    if (result.hasErrors()) {
        msg.addFlashAttribute("erroinserir", "Erro ao realizar o cadastro");
        return "redirect:/veiculos/inserirVeiculos";
    }

    var veiculosModel = new VeiculosModel();
    BeanUtils.copyProperties(veiculosDTO, veiculosModel);

    if (!imagem.isEmpty()) {
        try {
            byte[] bytes = imagem.getBytes();
            Path caminho = Paths.get("src/main/resources/static/img/", imagem.getOriginalFilename());
            Files.write(caminho, bytes);
            veiculosModel.setImagem(imagem.getOriginalFilename());
        } catch (IOException e) {
            System.out.println("Erro na imagem: " + e.getMessage());
        }        
    }

    Optional<CategoriasModel> categoriaOptional = categoriasRepository.findById(veiculosDTO.getCategoria_id());
    if (categoriaOptional.isPresent()) {
        veiculosModel.setCategoria(categoriaOptional.get());
    } else {

        msg.addFlashAttribute("erroinserir", "Categoria não encontrada");
        return "redirect:/veiculos/inserirVeiculos";
    }

    veiculosRepositorio.save(veiculosModel);
    msg.addFlashAttribute("mensagem", "Inserido com sucesso!");
    return "redirect:/veiculos/listarVeiculos";
}

    


    @GetMapping("/listarVeiculos")
    public ModelAndView listarVeiculos() {
        ModelAndView mv = new ModelAndView("veiculos/listarVeiculos");
        List<VeiculosModel> veiculos = veiculosRepositorio.findAll();
        mv.addObject("veiculos", veiculos);
        return mv;
    }

    @PostMapping("/listarVeiculos")
    public ModelAndView listarVeiculosSRC(@RequestParam("src") String src) {
        ModelAndView mv = new ModelAndView("veiculos/listarVeiculos");
        List<VeiculosModel> veiculos = veiculosRepositorio.findVeiculosByModeloLike("%" + src + "%");
        mv.addObject("veiculos", veiculos);
        return mv;
    }
    @GetMapping("/detalhesVeiculo")
    public ModelAndView detalhesVeiculo(@RequestParam("id") int id) {
        ModelAndView mv = new ModelAndView("veiculos/detalhesVeiculo");
        Optional<VeiculosModel> veiculo = veiculosRepositorio.findById(id);
        if (veiculo.isPresent()) {
            mv.addObject("veiculo", veiculo.get());
        } else {
            mv.addObject("mensagem", "Veículo não encontrado");
        }
        return mv;
    }

    @GetMapping("/listarVeiculosPorCategoria/{categoriaId}")
public String listarVeiculosPorCategoria(@PathVariable("categoriaId") int categoriaId, Model model) {
    Optional<CategoriasModel> categoriaOptional = categoriasRepository.findById(categoriaId);
    if (categoriaOptional.isPresent()) {
        CategoriasModel categoria = categoriaOptional.get();
        model.addAttribute("categoria", categoria);

        List<VeiculosModel> veiculos = veiculosRepositorio.findByCategoria_CategoriaId(categoriaId);
        model.addAttribute("veiculos", veiculos);

        return "veiculos/listarVeiculosPorCategoria";
    } else {
        return "redirect:/paginaDeErro";
    }
}

@GetMapping("/editarVeiculo/{id_veiculos}")
public ModelAndView formularioEditarVeiculo(@PathVariable("id_veiculos") int id) {
    ModelAndView mv = new ModelAndView("veiculos/editarVeiculo");

    Optional<VeiculosModel> veiculoOptional = veiculosRepositorio.findById(id);
    
    if (veiculoOptional.isPresent()) {
        VeiculosModel veiculo = veiculoOptional.get();
        mv.addObject("veiculo", veiculo);
        
        ResponseEntity<List<CategoriasModel>> response = categoriasController.listarTodasCategorias();
        List<CategoriasModel> categorias = response.getBody();
        mv.addObject("categorias", categorias);
        
        return mv;
    } else {
        return new ModelAndView("redirect:/paginaDeErro");
    }
}

@PostMapping("/editarVeiculo/{id_veiculos}")
public String salvarEdicaoVeiculo(@PathVariable("id_veiculos") int id, 
                                  @ModelAttribute @Valid VeiculosRecordDto veiculosDTO, 
                                  BindingResult result,
                                  @RequestParam("file") MultipartFile imagem) {

    Optional<VeiculosModel> veiculoOptional = veiculosRepositorio.findById(id);
    if (veiculoOptional.isPresent()) {
        VeiculosModel veiculo = veiculoOptional.get();
        BeanUtils.copyProperties(veiculosDTO, veiculo);

        if (!imagem.isEmpty()) {
            try {
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get("src/main/resources/static/img/", imagem.getOriginalFilename());
                Files.write(caminho, bytes);
                veiculo.setImagem(imagem.getOriginalFilename());
            } catch (IOException e) {
                System.out.println("Erro na imagem: " + e.getMessage());
            }        
        }

        veiculosRepositorio.save(veiculo);
        return "redirect:/veiculos/listarVeiculos";
    } else {
        return "redirect:/paginaDeErro";
    }
}



}