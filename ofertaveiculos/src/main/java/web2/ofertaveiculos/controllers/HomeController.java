package web2.ofertaveiculos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web2.ofertaveiculos.model.CategoriasModel;
import web2.ofertaveiculos.repositories.CategoriasRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<CategoriasModel> categorias = categoriasRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "nav";
    }
}