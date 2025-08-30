package com.example.appimc;

import java.io.Serializable;

public class Mulher implements State, Serializable {
    public Mulher() {
        super();
    }

    @Override
    public IMC calcImc(Pessoa pessoa) {
        String cond = "";
        double imc = pessoa.getPeso() / Math.pow(pessoa.getAltura(), 2);
        if (imc < 19.1)
            cond = "abaixo do peso";
        else if (imc < 25.8)
            cond = "no peso normal";
        else if (imc < 27.3)
            cond = "pouco acima do peso normal";
        else if (imc < 32.3)
            cond = "acima do peso normal";
        else
            cond = "obesa";

        return new IMC(imc, cond);
    }
}
