package com.examenML.prueba.controller;

import com.examenML.prueba.exception.DnaException;
import com.examenML.prueba.model.entity.Estadisticas;
import com.examenML.prueba.model.entity.Mutante;
import com.examenML.prueba.model.entity.Reclutamiento;
import com.examenML.prueba.service.IPruebaService;
import com.examenML.prueba.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PruebaImpl {

    @Autowired
    private ServiceImpl ServicioMatrix;

    @RequestMapping(method = RequestMethod.POST, value = "/mutant/")
    @ResponseBody
    public ResponseEntity<String> isMutant(@RequestBody Mutante mutante) throws DnaException {

        String[] dna = mutante.getDna().toArray((new String[0]));
        boolean isMutant = ServicioMatrix.getEstadoRegistro(dna);

        ResponseEntity<String> response;

        if (isMutant) {
            response = new ResponseEntity<String>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @RequestMapping("/stats")
   public Estadisticas getResultadoAnalisis() {
       return ServicioMatrix.getResultadoAnalisis();
    }

}
