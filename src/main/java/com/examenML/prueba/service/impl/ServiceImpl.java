package com.examenML.prueba.service.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.examenML.prueba.Utils.Adicional;
import com.examenML.prueba.Utils.Verificacion;
import com.examenML.prueba.exception.DnaException;
import com.examenML.prueba.model.entity.Estadisticas;
import com.examenML.prueba.model.entity.Reclutamiento;
import com.examenML.prueba.model.repository.ReclutamientoRepository;
import com.examenML.prueba.service.IPruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Service
public class ServiceImpl {

    @Autowired
    private ReclutamientoRepository reclutamientoRepository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    //Metodo para validacion de combinaciones verticales, horizontales y oblicuas de Dna

    public boolean isMutant(String[] dna) throws DnaException {
        int mutanteDna = 0;

        try {
            Adicional.comprobacionMatrix(dna);

            for (int i = 0; i < dna.length; i++) {
                for (int j = 0; j < dna[i].length(); j++) {

                    // Verificacion de patron horizontal
                    if (j < dna[i].length() - 3) {

                        if (Verificacion.comparacionDna(dna[i].charAt(j), dna[i].charAt(j + 1), dna[i].charAt(j + 2),
                                dna[i].charAt(j + 3))) {
                            mutanteDna++;
                        }
                    }

                    // Verificacion de patron vertical
                    if (i < dna[i].length() - 3) {
                        if (Verificacion.comparacionDna(dna[i].charAt(j), dna[i + 1].charAt(j), dna[i + 2].charAt(j),
                                dna[i + 3].charAt(j))) {
                            mutanteDna++;
                        }
                    }

                    // Verificacion de patron Oblicuo, Derecha a izquierda y Arriba a abajo
                    if (j < dna[i].length() - 3 && i < dna[i].length() - 3) {
                        if (Verificacion.comparacionDna(dna[i].charAt(j), dna[i + 1].charAt(j + 1),
                                dna[i + 2].charAt(j + 2), dna[i + 3].charAt(j + 3))) {
                            mutanteDna++;
                        }
                    }

                    // Verificacion de patron  Oblicuo, Izquierda a derecha y Abajo a arriba
                    if (dna[i].length() > 3 && j < dna[i].length() - 3 && i > 2) {
                        if (Verificacion.comparacionDna(dna[i].charAt(j), dna[i - 1].charAt(j + 1),
                                dna[i - 2].charAt(j + 2), dna[i - 3].charAt(j + 3))) {
                            mutanteDna++;
                        }
                    }

                    if (mutanteDna >= 2) {
                        return true;
                    }
                }

            }

        } catch (DnaException e) {
            throw e;
        }

        return false;
    }

    // Metodo que retorna las estadisticas del proceso de analisis Dna
    public Estadisticas getResultadoAnalisis() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("dna", new AttributeValue().withS("DNA"));

        GetItemResult result = amazonDynamoDB.getItem("seleccion", key);

        Estadisticas resultado;
        if (result.getItem() != null) {

            AttributeValue count_mutant_dna = result.getItem().get("count_mutant_dna");

            AttributeValue count_human_dna = result.getItem().get("count_human_dna");
            resultado = new Estadisticas(Integer.parseInt(count_mutant_dna.getN()), Integer.parseInt(count_human_dna.getN()));


        } else {
            resultado = new Estadisticas(0, 0);
        }
        return resultado;
    }


    //Metodo que retorna el estado de registro del dna en la tabla
    public boolean getEstadoRegistro(String[] dna) throws DnaException {

        String dnaPersona = Arrays.toString(dna);
        Reclutamiento reclutamiento = reclutamientoRepository.findOne(dnaPersona);
        boolean estado;

        if (reclutamiento != null) {

            estado = reclutamiento.getisMutant();

        } else {
            estado = this.isMutant(dna);

            this.validacionDna(dnaPersona, estado);
        }

        return estado;

    }

    //Metodo que valida de acuerdo a la condicion de Dna si es mutante o humano

    public void validacionDna(String dnaPersona, boolean analisisDna) {

        String cantidad;
        reclutamientoRepository.save(new Reclutamiento(dnaPersona, analisisDna));

        if (analisisDna) {
            cantidad = "count_mutant_dna";
        } else {
            cantidad = "count_human_dna";
        }

        this.contadorDna(cantidad);
    }

    public void contadorDna(String cantidad) {

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("dna", new AttributeValue().withS("DNA"));
        UpdateItemRequest updateItemRequest = new UpdateItemRequest().withTableName("seleccion").withKey(key)
                .addAttributeUpdatesEntry(cantidad, new AttributeValueUpdate().withValue(new AttributeValue().withN("1"))
                        .withAction(AttributeAction.ADD));
        amazonDynamoDB.updateItem(updateItemRequest);

    }


}


