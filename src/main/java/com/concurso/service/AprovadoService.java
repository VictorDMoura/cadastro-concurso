package com.concurso.service;

import com.concurso.model.Aprovado;
import com.concurso.repository.AprovadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AprovadoService {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
    private static final String[] ALLOWED_TYPES = {"image/jpeg", "image/jpg", "image/png"};

    @Autowired
    private AprovadoRepository aprovadoRepository;

    public List<Aprovado> listarTodos() {
        return aprovadoRepository.findAllByOrderByDataCadastroDesc();
    }

    public Aprovado buscarPorId(Long id) {
        return aprovadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aprovado não encontrado"));
    }

    public void salvar(Aprovado aprovado, MultipartFile imagem) throws IOException {
        validarDadosBasicos(aprovado);
        validarImagemEArmazenar(aprovado, imagem);
        aprovadoRepository.save(aprovado);
    }

    public void deletar(Long id) {
        aprovadoRepository.deleteById(id);
    }

    public String obterMimeType(String nomeArquivo) {
        if (nomeArquivo == null) return "image/png";
        String lower = nomeArquivo.toLowerCase();
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
        if (lower.endsWith(".png")) return "image/png";
        return "image/png";
    }

    private void validarDadosBasicos(Aprovado aprovado) {
        if (aprovadoRepository.existsByEmail(aprovado.getEmail()) && aprovado.getId() == null) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        if (aprovado.getConcursos() == null || aprovado.getConcursos().isEmpty()) {
            throw new IllegalArgumentException("Informe pelo menos um concurso");
        }
    }

    private void validarImagemEArmazenar(Aprovado aprovado, MultipartFile imagem) throws IOException {
        if (imagem == null || imagem.isEmpty()) {
            throw new IllegalArgumentException("A imagem é obrigatória");
        }

        validarImagem(imagem);
        aprovado.setImagem(imagem.getBytes());
        aprovado.setImagemNome(imagem.getOriginalFilename());
    }

    private void validarImagem(MultipartFile imagem) {
        if (imagem.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Imagem muito grande. Tamanho máximo: 2MB");
        }

        String contentType = imagem.getContentType();
        boolean tipoValido = false;
        for (String tipo : ALLOWED_TYPES) {
            if (tipo.equals(contentType)) {
                tipoValido = true;
                break;
            }
        }

        if (!tipoValido) {
            throw new IllegalArgumentException("Tipo de arquivo inválido. Use: JPEG, JPG ou PNG");
        }

        if (imagem.isEmpty()) {
            throw new IllegalArgumentException("Arquivo de imagem está vazio");
        }
    }
}
