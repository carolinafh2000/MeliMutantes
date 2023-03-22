package com.examenML.prueba;
import com.examenML.prueba.Utils.Adicional;
import com.examenML.prueba.Utils.Verificacion;
import com.examenML.prueba.exception.DnaException;
import com.examenML.prueba.exception.ErrorDnaException;
import com.examenML.prueba.exception.ErrorSizeException;
import com.examenML.prueba.model.entity.Estadisticas;
import com.examenML.prueba.service.impl.ServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PruebaApplicationTests {
        ServiceImpl ServicioMatrix = new ServiceImpl();

        @Test
        public void testEstadisticasSinRegistros() {

                Estadisticas estadistica = new Estadisticas(0, 0);
                assertTrue(estadistica.getCount_human_dna() == 0.0);
                assertTrue(estadistica.getCount_mutant_dna() == 0.0);
                assertTrue(estadistica.getRatio() == 0.0);
        }

        @Test
        public void testEstadisticasSinHumanos() {

                Estadisticas estadistica = new Estadisticas(1, 0);
                assertTrue(estadistica.getCount_human_dna() == 0.0);
                assertTrue(estadistica.getCount_mutant_dna() == 1.0);
                assertTrue(estadistica.getRatio() == 1.0);
        }

        @Test
        public void testEstadisticasHumanos_Mutantes() {

                Estadisticas estadistica = new Estadisticas(40, 100);
                assertTrue(estadistica.getCount_human_dna() == 100.0);
                assertTrue(estadistica.getCount_mutant_dna() == 40.0);
                assertTrue(estadistica.getRatio() == 0.4);
        }

        @Test
        public void testValidacionHorizontalCaso1() throws DnaException {
                String[] dna = { "AAAA", "CCCC", "GACT", "GACT" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testValidacionHorizontalCaso2() throws DnaException {
                String[] dna = { "GACTA", "GGGGT", "GTTTT", "CGTAC", "TTGCA" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testValidacionHorizontalCaso3() throws DnaException {
                String[] dna = { "GACTAC", "GACGTA", "AAGCTA", "GTGGGG", "GTACAG", "ATTTTA" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testValidacionVerticalCaso1() throws DnaException {
                String[] dna = { "GACT", "GACT", "GACT", "CTCT" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testValidacionVerticalCaso2() throws DnaException {
                String[] dna = { "AATAA", "CATGC", "TATCG", "CATAC", "CCGTC" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testValidacionVerticalCaso3() throws DnaException {
                String[] dna = { "GACTAC", "GACTAC", "GTCTAC", "ATGCGC", "GTTCAT", "ATGCGA" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testValidacionDiagonalCaso1() throws DnaException {
                String[] dna = { "GACC", "AGCC", "GCGT", "CTCG" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testMutanteCaso1() throws DnaException {
                String[] dna = { "ATGA", "CGAC", "TAAT", "AAAA" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testMutanteCaso2() throws DnaException {
                String[] dna = { "ATGAGA", "CAATGC", "TAATGT", "AGAAGG", "CCGCTA", "TCATGC" };
                assertTrue(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testHumanoCaso1() throws DnaException {
                String[] dna = { "ATGC", "CATC", "TAGT", "GTCT" };
                assertFalse(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testHumanoCaso2() throws DnaException {
                String[] dna = { "ATGCG", "CATCA", "TAGTT", "GTCTG", "GTACC" };
                assertFalse(ServicioMatrix.isMutant(dna));
        }

        @Test
        public void testHumanoCaso3() throws DnaException {
                String[] dna = { "ATGCGA", "CATCTA", "TAGTAT", "GTCTGG", "GTCACC", "ATTGCA" };
                assertFalse(ServicioMatrix.isMutant(dna));
        }

        @Test(expected = DnaException.class)
        public void testValidacionMutante() throws DnaException {
                String[] dna = { "QTAC", "CATC", "TAGT", "GTCT" };
                ServicioMatrix.isMutant(dna);
        }

        @Test(expected = ErrorSizeException.class)
        public void testTamañoCadenaInvalido() throws DnaException {
                String[] dna = { "ATGC", "CATC", "TAGT", "GTCTA" };
                Adicional.comprobacionMatrix(dna);
        }

        @Test(expected = ErrorDnaException.class)
        public void testSecuenciaInvalida() throws DnaException {
                String[] dna = { "QTGC", "CATC", "TAGT", "GTCT" };
                Adicional.comprobacionMatrix(dna);
        }

        @Test
        public void testComparacionDnaCaso1() {
                assertTrue(Verificacion.comparacionDna('a', 'a', 'a', 'a'));
        }

        @Test
        public void testComparacionDnaCaso2() {
                assertFalse(Verificacion.comparacionDna('a', 'b', 'c', 'd'));
        }

        @Test
        public void testComparacionDnaCaso3() {
                assertFalse(Verificacion.comparacionDna('a', 'a', 'b', 'b'));
        }

        @Test
        public void testComparacionDnaCaso4() {
                assertFalse(Verificacion.comparacionDna('a', 'a', 'a', 'b'));
        }


}
