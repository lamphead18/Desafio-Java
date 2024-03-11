package com.example.tgid.helper;

import java.math.BigDecimal;

public class Tarifa {
    private BigDecimal taxa;
    private BigDecimal valorReajustado;

    public Tarifa(BigDecimal valorReajustado) {
        this.taxa = BigDecimal.valueOf(0.9);
        this.valorReajustado = valorReajustado.multiply(taxa);
    }
    public BigDecimal getValorReajustado() {
        return valorReajustado;
    }
}
