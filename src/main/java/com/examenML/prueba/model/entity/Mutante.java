package com.examenML.prueba.model.entity;

import java.util.List;

public class Mutante {

        public Mutante(List<String> dna) {
            super();
            this.dna = dna;
        }

        public Mutante() {
        }

        private List<String> dna;

        public List<String> getDna() {
            return dna;
        }

}
