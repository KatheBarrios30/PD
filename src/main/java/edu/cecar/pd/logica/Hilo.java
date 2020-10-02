
package edu.cecar.pd.logica;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Hilo implements Runnable{
    
    HashMap<String, BigDecimal> resultado= new HashMap<String, BigDecimal>();
    ArrayList<String> fecha;
    ArrayList<String> importes;

    public Hilo(ArrayList<String> fecha, ArrayList<String> importes) {
    this.fecha = fecha;
    this.importes = importes;
    }
    
    
    
    
    @Override
    public void run() {
        BigDecimal bd = new BigDecimal(BigInteger.ZERO);
       
        for (int i = 0; i < importes.size(); i++) {
            
            if(resultado.containsKey(fecha.get(i))){
                
                BigDecimal aux = resultado.get(fecha.get(i));
                aux = aux.add(new BigDecimal(importes.get(i)));
                resultado.put(fecha.get(i), aux);
                
            }else{
                resultado.put(fecha.get(i), new BigDecimal(importes.get(i)));
            }
            
        }
    }
    public HashMap<String, BigDecimal> getResultado() {
        return resultado;
    }
}
