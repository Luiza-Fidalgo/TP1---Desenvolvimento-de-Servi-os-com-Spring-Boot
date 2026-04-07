package com.guilda.api.dto;
import com.guilda.api.model.Classe;

public class AventureiroResumoDTO {
    private Long id;
    private String nome;
    private Classe classe;
    private int nivel;
    private boolean ativo;

    public AventureiroResumoDTO(Long id, String nome, Classe classe, int nivel, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Classe getClasse() { return classe; }
    public int getNivel() { return nivel; }
    public boolean isAtivo() { return ativo; }
}