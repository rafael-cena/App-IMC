package com.example.appimc;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Pessoa implements Serializable {
    private String nome, sexo;
    private double peso, altura;
    private IMC imc;
    private State atual;
    private boolean exb;

    public Pessoa(String nome, double peso, double altura, String sexo) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        if (sexo.equals("masculino"))
            this.atual = new Homem();
        else
            this.atual = new Mulher();
        this.exb = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public State getAtual() {
        return atual;
    }

    public void setAtual(State atual) {
        this.atual = atual;
    }

    public IMC getImc() {
        return atual.calcImc(this);
    }

    public void setImc() {
        this.imc = atual.calcImc(this);
    }

    public boolean isExb() {
        return exb;
    }

    public void setExb(boolean exb) {
        this.exb = exb;
    }

    @NonNull
    @Override
    public String toString() {
        return "" + this.getNome() + ", no auge dos seus " + String.format("%.1f", this.getPeso()) +
                "kg e com " + String.format("%.2f", this.getAltura()) +
                "m, seu IMC é " + String.format("%.1f", this.imc.getImc()) +
                ", você está " + this.imc.getCondicao();
    }
}
