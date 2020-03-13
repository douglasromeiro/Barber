package com.example.barber.model;

import android.content.Intent;

public class Usuario {
    private String nome;
    private String email;
    private String endereco;
    private String complemento;
    private String senha;
    private String dataNascimento;
    private Integer cep;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String endereco, String complemento, String senha, String dataNascimento, Integer cep) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.complemento = complemento;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getSenha() {
        return senha;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public Integer getCep() {
        return cep;
    }
}
