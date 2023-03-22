package com.examenML.prueba.Utils;

import com.examenML.prueba.exception.DnaException;
import com.examenML.prueba.exception.ErrorDnaException;
import com.examenML.prueba.exception.ErrorSizeException;

public class Adicional {
    public static boolean comprobacionMatrix(String[] dna) throws DnaException {

        int dnaTamaño = dna.length;
        String validStrings = "[ACGT]+";

        for (String dnaSerie : dna) {
            if (dnaSerie.length() != dnaTamaño) {
                throw new ErrorSizeException();
            }

            if (!dnaSerie.matches(validStrings)) {
                throw new ErrorDnaException();
            }

        }

        return true;
    }
}
