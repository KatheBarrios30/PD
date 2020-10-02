/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cecar.pd.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class RepartirDatosEnHilos {

    static int numeroCores = Runtime.getRuntime().availableProcessors();
    //static int numeroCores = 2;
    Map<String, BigDecimal> resultado = new HashMap<String, BigDecimal>();
    Map<LocalDate, BigDecimal> resultadosOrdenados;
    JLabel mensjae;

    public RepartirDatosEnHilos(JLabel mensjae) {
        this.mensjae = mensjae;
    }
    

    
    

    private ArrayList[][] repartirDatosEnPaquetes(File carpeta) {

        BufferedReader br = null;
        ArrayList[][] matriz = new ArrayList[numeroCores][2];
        String linea;
        int core = 0;
        try {

            for (int i = 0; i < numeroCores; i++) {
                matriz[i][0] = new ArrayList();
                matriz[i][1] = new ArrayList();

            }
            br = new BufferedReader(new FileReader(carpeta));
            br.readLine();

            while ((linea = br.readLine()) != null) {
                if (linea.length() < 4) {
                    continue;
                }

                String datos[] = linea.split(",");

                matriz[core][0].add(datos[2]);
                matriz[core][1].add(datos[3]);

                core++;
                if (core >= numeroCores) {

                    core = 0;

                }

            }
            br.close();

        } catch (Exception ex) {
            Logger.getLogger(RepartirDatosEnHilos.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return matriz;
    }

    public void iniciarCargarPaquetesEnHilos() {
        System.out.println("init proceso datos");
        
        mensjae.setText("Estado procesoDatos: procesando datos");
        
        
        Thread[] hilos = new Thread[numeroCores];
        Hilo[] tareas = new Hilo[numeroCores];

        File carpeta = new File("src/main/java/edu/cecar/pd/recursos");

        for (File archivo : carpeta.listFiles()) {
            //ArrayList[][] datosRepartidos = repartirDatosEnPaquetes(new File("src/main/java/edu/cecar/pd/recursos/1.csv"));
            
            if(archivo.isDirectory()) continue;
            
            ArrayList[][] datosRepartidos = repartirDatosEnPaquetes(archivo);
            for (int i = 0; i < numeroCores; i++) {
                tareas[i] = new Hilo(datosRepartidos[i][0], datosRepartidos[i][1]);
            }

            for (int i = 0; i < numeroCores; i++) {
                hilos[i] = new Thread(tareas[i]);
                hilos[i].start();
            }

            try {
                for (int i = 0; i < numeroCores; i++) {
                    hilos[i].join();
                }

                for (Hilo tarea : tareas) {

                    for (String key : tarea.getResultado().keySet()) {

                        if (resultado.containsKey(key)) {

                            BigDecimal aux = tarea.getResultado().get(key);
                            aux = aux.add(resultado.get(key));
                            resultado.remove(key);
                            resultado.put(key, aux);

                        } else {
                            resultado.put(key, tarea.getResultado().get(key));
                        }

                    }

                }

            } catch (InterruptedException ex) {
                Logger.getLogger(RepartirDatosEnHilos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        resultadosOrdenados = new TreeMap<LocalDate, BigDecimal>();
        for (String key : resultado.keySet()) {
            resultadosOrdenados.put(LocalDate.parse(key, DateTimeFormatter.ofPattern("yyyy-MM-dd")), resultado.get(key));
        }
        System.out.println("fin proceso datos");
        mensjae.setText("Estado procesoDatos: listo");
    }

    public Map<LocalDate, BigDecimal> getResultadosOrdenados() {
        return resultadosOrdenados;
    }
    
    

}
