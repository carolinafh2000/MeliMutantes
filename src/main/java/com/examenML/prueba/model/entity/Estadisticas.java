package com.examenML.prueba.model.entity;

public class Estadisticas {
    public Estadisticas(double count_mutant_dna, double count_human_dna) {
        super();
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;

        if (count_mutant_dna == 0 && count_human_dna == 0) {
            this.ratio = 0.0;
        } else {
            if (count_human_dna == 0) {
                this.ratio = 1;
            } else {
                this.ratio = (count_mutant_dna / count_human_dna);
            }
        }

    }

    private double count_mutant_dna;
    private double count_human_dna;
    private double ratio;

    public double getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public double getCount_human_dna() {
        return count_human_dna;
    }

    public double getRatio() {
        return ratio;
    }
}
