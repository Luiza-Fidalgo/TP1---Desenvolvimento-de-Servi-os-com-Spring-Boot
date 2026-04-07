package com.guilda.api.controller;

import com.guilda.api.dto.AventureiroResumoDTO;
import com.guilda.api.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    private List<Aventureiro> database = new ArrayList<>();
    private Long nextId = 1L;

    // 1 Registrar
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Aventureiro novo) {
        List<String> erros = validarAventureiro(novo);
        if (!erros.isEmpty()) return responderErro(erros);

        novo.setId(nextId++);
        novo.setAtivo(true);
        novo.setCompanheiro(null);
        database.add(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // 2 Listar com Paginação e Filtros
    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> listar(
            @RequestParam(required = false) Classe classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Aventureiro> filtrados = database.stream()
                .filter(a -> (classe == null || a.getClasse() == classe))
                .filter(a -> (ativo == null || a.isAtivo() == ativo))
                .filter(a -> (nivelMin == null || a.getNivel() >= nivelMin))
                .sorted(Comparator.comparing(Aventureiro::getId))
                .collect(Collectors.toList());

        int totalItens = filtrados.size();
        int totalPaginas = (int) Math.ceil((double) totalItens / size);
        int start = Math.min(page * size, totalItens);
        int end = Math.min(start + size, totalItens);

        List<AventureiroResumoDTO> resumo = filtrados.subList(start, end).stream()
                .map(a -> new AventureiroResumoDTO(a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.isAtivo()))
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(totalItens));
        headers.add("X-Page", String.valueOf(page));
        headers.add("X-Size", String.valueOf(size));
        headers.add("X-Total-Pages", String.valueOf(totalPaginas));

        return new ResponseEntity<>(resumo, headers, HttpStatus.OK);
    }

    // 3 Consultar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable Long id) {
        return database.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    // 7 Definir Companheiro
    @PutMapping("/{id}/companheiro")
    public ResponseEntity<?> definirCompanheiro(@PathVariable Long id, @RequestBody Companheiro comp) {
        Aventureiro avent = database.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
        if (avent == null) return ResponseEntity.notFound().build();

        if (comp.getNome() == null || comp.getEspecie() == null || comp.getLealdade() < 0 || comp.getLealdade() > 100) {
            return responderErro(Arrays.asList("Dados do companheiro inválidos"));
        }

        avent.setCompanheiro(comp);
        return ResponseEntity.ok(avent);
    }

    // Métodos Auxiliares
    private List<String> validarAventureiro(Aventureiro a) {
        List<String> erros = new ArrayList<>();
        if (a.getNome() == null || a.getNome().trim().isEmpty()) erros.add("nome é obrigatório");
        if (a.getClasse() == null) erros.add("classe inválida ou obrigatória");
        if (a.getNivel() < 1) erros.add("nivel deve ser maior ou igual a 1");
        return erros;
    }

    private ResponseEntity<?> responderErro(List<String> detalhes) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("mensagem", "Solicitação inválida");
        erro.put("detalhes", detalhes);
        return ResponseEntity.badRequest().body(erro);
    }
}