/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AeroportoDAO;
import br.edu.ifsul.dao.CidadeDAO;
import br.edu.ifsul.modelo.Aeroporto;
import br.edu.ifsul.modelo.Cidade;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leticia-PC
 */
@ManagedBean(name = "controleAeroporto")
@SessionScoped
public class ControleAeroporto implements Serializable{
    private AeroportoDAO dao;
    private Aeroporto objeto;    
    private CidadeDAO daoCidade;

    public ControleAeroporto() {
        dao = new AeroportoDAO();        
        daoCidade = new CidadeDAO();
    }

    public CidadeDAO getDaoCidade() {
        return daoCidade;
    }

    public void setDaoCidade(CidadeDAO daoCidade) {
        this.daoCidade = daoCidade;
    }
    
    public String listar(){
        return "/privado/aeroporto/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Aeroporto();
        return "formulario?faces-redirect=true";
    }
    
    public String salvar(){
        if(dao.salvar(objeto)){
            Util.mensagemInformacao(dao.getMensagem());
            return "listar?faces-redirect=true";
        }else{
            Util.mensagemErro(dao.getMensagem());
            return "listar?faces-redirect=true";
        }
                
    }
    
    public String cancelar(){
        return "listar?faces-redirect=true";
    }
    
    public String editar(Integer id){
        objeto = dao.localizar(id);
        return "formulario?faces-redirect=true"; 
    }
    
    public void remover(Integer id){
        objeto = dao.localizar(id);
        if(dao.remover(objeto)){
            Util.mensagemInformacao(dao.getMensagem());
        }else{
            Util.mensagemErro(dao.getMensagem());
        }
    }

    public AeroportoDAO getDao() {
        return dao;
    }

    public void setDao(AeroportoDAO dao) {
        this.dao = dao;
    }

    public Aeroporto getObjeto() {
        return objeto;
    }

    public void setObjeto(Aeroporto objeto) {
        this.objeto = objeto;
    }
    
    
    
}
