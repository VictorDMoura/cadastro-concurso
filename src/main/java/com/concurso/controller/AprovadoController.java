package com.concurso.controller;

import com.concurso.model.Aprovado;
import com.concurso.service.AprovadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AprovadoController {

    @Autowired
    private AprovadoService aprovadoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("aprovados", aprovadoService.listarTodos());
        return "index";
    }

    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("aprovado", new Aprovado());
        return "formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Aprovado aprovado,
                        BindingResult result,
                        @RequestParam(value = "file", required = false) MultipartFile file,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        
        if (result.hasErrors()) {
            return "formulario";
        }

        try {
            aprovadoService.salvar(aprovado, file);
            redirectAttributes.addFlashAttribute("sucesso", 
                "Cadastro realizado com sucesso!");
            return "redirect:/";
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "formulario";
        } catch (IOException e) {
            model.addAttribute("erro", "Erro ao processar a imagem");
            return "formulario";
        }
    }

    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> exibirImagem(@PathVariable Long id) {
        try {
            Aprovado aprovado = aprovadoService.buscarPorId(id);
            
            if (aprovado.getImagem() == null) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            String mimeType = aprovadoService.obterMimeType(aprovado.getImagemNome());
            headers.setContentType(MediaType.parseMediaType(mimeType));
            
            return new ResponseEntity<>(aprovado.getImagem(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            aprovadoService.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Registro excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir registro");
        }
        return "redirect:/";
    }
}
