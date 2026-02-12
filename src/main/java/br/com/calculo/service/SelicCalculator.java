package br.com.calculo.service;

import br.com.calculo.domain.SelicInput;
import br.com.calculo.domain.SelicResult;

public interface SelicCalculator {
    SelicResult calculate(SelicInput input);
}
