package com.example.appimc;

import java.io.Serializable;

public class IMC implements Serializable {
    private double imc;
    private String condicao;

    public IMC(double imc, String condicao) {
        this.imc = imc;
        this.condicao = condicao;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }
}
