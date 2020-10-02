
package edu.cecar.pd.logica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


public class ConsultarDatosPorFecha {

    Map<LocalDate, BigDecimal> resultadosOrdenados;

    public ConsultarDatosPorFecha(Map<LocalDate, BigDecimal> resultadosOrdenados) {
        this.resultadosOrdenados = resultadosOrdenados;
    }
    
   
    public String importeTotal(){
        String resultado ="";
        BigDecimal aux = BigDecimal.ONE;
        
        for (LocalDate key : resultadosOrdenados.keySet()) {
            
            aux= aux.add(resultadosOrdenados.get(key));
            
            
        }
        resultado="importe Total: "+ aux.toString();
        
        return resultado;
    }
    
    public String importePorAnio(String dato){
        
        String resultado ="";
        int anio = Integer.valueOf(dato);
        BigDecimal aux = BigDecimal.ZERO;
        
        for (LocalDate key : resultadosOrdenados.keySet()) {
            
            if(key.getYear()==anio){
                aux= aux.add(resultadosOrdenados.get(key));
            }
            
            
            
        }
        resultado= "impote Total para el año "+dato+" es de: "+aux.toString();
        
        return resultado;
        
    }
    
     public String importePorAnioMes(String datoAnio, String datoMes){
        
        String resultado ="";
        int anio = Integer.valueOf(datoAnio);
        int mes = Integer.valueOf(datoMes);
        BigDecimal aux = BigDecimal.ZERO;
        
        for (LocalDate key : resultadosOrdenados.keySet()) {
            
            if(key.getYear()==anio && key.getMonthValue()==mes){
                aux= aux.add(resultadosOrdenados.get(key));
            }
            
            
            
        }
        resultado= "impote Total para la fecha "+anio+"/"+mes +" es de: "+aux.toString();
        
        return resultado;
        
    }
     public String importePorAnioTrimestre(String datoAnio, int datoTrimestre){
        
        String resultado ="";
        int anio = Integer.valueOf(datoAnio);
        int trimestre = datoTrimestre*3;
        BigDecimal aux = BigDecimal.ZERO;
         System.out.println(trimestre-3 +" "+trimestre);
        for (LocalDate key : resultadosOrdenados.keySet()) {
            
            if(key.getYear()==anio && (key.getMonthValue()>trimestre-3 && key.getMonthValue()<=trimestre)){
                aux= aux.add(resultadosOrdenados.get(key));
            }
            
            
            
        }
        resultado= "impote para el periodo "+anio+" trimestre: "+trimestre +" es de: "+aux.toString();
        
        return resultado;
        
    }
    
}
