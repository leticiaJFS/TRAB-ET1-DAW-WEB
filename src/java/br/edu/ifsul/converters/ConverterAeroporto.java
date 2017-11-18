package br.edu.ifsul.converters;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Aeroporto;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Leticia-PC
 */
@FacesConverter(value = "converterAeroporto")
public class ConverterAeroporto implements Serializable, Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null){
            return null;
        }
        try{
            return EntityManagerUtil.getEntityManager().find(Aeroporto.class, Integer.parseInt(string));                    
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null){
            return null;
        }
        Aeroporto obj = (Aeroporto) o;
        return  obj.getId().toString();
    }
    
}
