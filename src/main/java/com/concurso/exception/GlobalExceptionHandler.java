package com.concurso.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, 
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        model.addAttribute("erro", "Arquivo muito grande! Tamanho máximo permitido: 2MB");
        return "formulario";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException exc, 
                                       Model model) {
        model.addAttribute("erro", exc.getMessage());
        return "formulario";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception exc, 
                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", "Ocorreu um erro: " + exc.getMessage());
        return "redirect:/";
    }
}
