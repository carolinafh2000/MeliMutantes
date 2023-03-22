package com.examenML.prueba.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "reclutamiento")

public class Reclutamiento {
    public Reclutamiento() {
    }

    public Reclutamiento(String dna, boolean validacionMutante) {
        super();
        this.dna = dna;
        this.isMutant = isMutant;
    }

    private String dna;
    private boolean isMutant;

    @DynamoDBHashKey(attributeName = "Dna")
    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    @DynamoDBAttribute
    public boolean getisMutant() {
        return isMutant;
    }

    public void setisMutant(boolean isMutant) {
        this.isMutant = isMutant;
    }

}

