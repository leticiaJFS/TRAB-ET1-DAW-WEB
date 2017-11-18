package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Voo;
import java.io.Serializable;

/**
 *
 * @author Leticia-PC
 */
public class VooDAO<T> extends DAOGenerico<Voo> implements Serializable{
    
    public VooDAO(){
        super();
        classPersistence = Voo.class;
        ordem = "descricao";
                
    }
    
}
