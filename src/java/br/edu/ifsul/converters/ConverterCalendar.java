package br.edu.ifsul.converters;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Leticia-PC
 */
@FacesConverter(value="converterCalendar")
public class ConverterCalendar implements Converter, Serializable{
    SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
    try{
        Calendar obj = Calendar.getInstance();
        obj.setTime(SDF.parse(string));
        return obj;
    }catch(Exception e){
        return null;
    }  
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null){
            return null;
        }
        
        Calendar obj = (Calendar)o;
        return SDF.format(obj.getTime());
    }
    
}
