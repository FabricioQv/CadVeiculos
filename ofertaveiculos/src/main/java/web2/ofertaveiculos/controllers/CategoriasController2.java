package web2.ofertaveiculos.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import web2.ofertaveiculos.dto.CategoriasRecordDto;
import web2.ofertaveiculos.model.CategoriasModel;
import web2.ofertaveiculos.repositories.CategoriasRepository;
import web2.ofertaveiculos.repositories.VeiculosRepository;

@Controller
@RequestMapping("/categorias")
public class CategoriasController2 {

    @Autowired
    CategoriasRepository categoriasRepositorio;

    @Autowired
    VeiculosRepository veiculosRepositorio;

    @GetMapping("/inserirCategorias")
    public String formularioCategorias(){
        return "categorias/inserirCategorias";
    }

    @PostMapping("/inserirCategorias")
	public String salvarCategoria
		(@ModelAttribute @Valid CategoriasRecordDto categoriasDTO,
				BindingResult result, RedirectAttributes msg){
		if(result.hasErrors()) {
			msg.addFlashAttribute("erroinserir", "Erro ao realizar o cadastro");
			return "redirect:/categorias/inserirCategorias";
		}
		var categoriasModel = new CategoriasModel();
		BeanUtils.copyProperties(categoriasDTO, categoriasModel);
		categoriasRepositorio.save(categoriasModel);
		msg.addFlashAttribute("mensagem", "Inserido com sucesso!");
		return "redirect:/categorias/list";
	}

    @GetMapping("/listarCategorias")
	public ModelAndView listarCategorias(){
		ModelAndView mv = new ModelAndView("categorias/listarCategorias");
		List<CategoriasModel> categorias = 	categoriasRepositorio.findAll();
		mv.addObject("categorias", categorias);
		return mv;
	}

    @PostMapping("/listarCategorias")
	public ModelAndView listarCategoriasSRC(@RequestParam("src") String src){
		ModelAndView mv = new ModelAndView("categorias/listarCategorias");
		List<CategoriasModel> categorias = 	categoriasRepositorio.findCategoriasByNomeLike("%" +src+"%");
		mv.addObject("categorias", categorias);
		return mv;
	}


    

}
