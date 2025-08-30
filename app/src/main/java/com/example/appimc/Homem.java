package com.example.appimc;

import java.io.Serializable;

public class Homem implements State, Serializable {
    Homem() {
        super();
    }

    @Override
    public IMC calcImc(Pessoa pessoa) {
        String cond = "";
        double imc = pessoa.getPeso() / Math.pow(pessoa.getAltura(), 2);
        if (imc < 20.7)
            cond = "abaixo do peso";
        else if (imc < 26.4)
            cond = "no peso normal";
        else if (imc < 27.8)
            cond = "pouco acima do peso normal";
        else if (imc < 31.1)
            cond = "acima do peso normal";
        else
            cond = "obeso";

        return new IMC(imc, cond);
    }
}
