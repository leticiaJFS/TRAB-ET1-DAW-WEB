/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leticia-PC
 */
@ManagedBean(name = "controlePessoa")
@SessionScoped
public class ControlePessoa implements Serializable{
    private PessoaDAO dao;
    private Pessoa objeto;

    public ControlePessoa() {
        dao = new PessoaDAO();
    }
    
    public String listar(){
        return "/privado/pessoa/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Pessoa();
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
        return "formulario?faces-redirect=true"; 
    }
    
    public void remover(Integer id){
        if(dao.remover(objeto)){
            Util.mensagemInformacao(dao.getMensagem());
        }else{
            Util.mensagemErro(dao.getMensagem());
        }
    }


    public PessoaDAO getDao() {
        return dao;
    }

    public void setDao(PessoaDAO dao) {
        this.dao = dao;
    }

    public Pessoa getObjeto() {
        return objeto;
    }

    public void setObjeto(Pessoa objeto) {
        this.objeto = objeto;
    }
    
    
    
}
