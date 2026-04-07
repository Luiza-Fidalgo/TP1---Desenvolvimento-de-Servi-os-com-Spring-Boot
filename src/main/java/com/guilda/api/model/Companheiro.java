package com.guilda.api.model;

public class Companheiro {
    private String nome;
    private Especie especie;
    private int lealdade;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }
    public int getLealdade() { return lealdade; }
    public void setLealdade(int lealdade) { this.lealdade = lealdade; }
}