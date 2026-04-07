package com.guilda.api.model;

public class Aventureiro {
    private Long id;
    private String nome;
    private Classe classe;
    private int nivel;
    private boolean ativo = true;
    private Companheiro companheiro;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public Companheiro getCompanheiro() { return companheiro; }
    public void setCompanheiro(Companheiro companheiro) { this.companheiro = companheiro; }
}