package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AeroportoDAO;
import br.edu.ifsul.dao.CidadeDAO;
import br.edu.ifsul.dao.VooDAO;
import br.edu.ifsul.modelo.Aeroporto;
import br.edu.ifsul.modelo.Voo;
import br.edu.ifsul.modelo.VooAgendado;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Leticia-PC
 */

@ManagedBean(name = "controleVoo")
@ViewScoped
public class ControleVoo implements Serializable{
    private VooDAO<Voo> dao;
    private Voo objeto;
    private VooAgendado vooAgendado;
    private Boolean novoVooAgendado;
    private Aeroporto aeroporto;
    private AeroportoDAO daoAeroporto;
    
    public ControleVoo() {
        dao = new VooDAO<>();
        daoAeroporto = new AeroportoDAO();
    }
    
    public void adicionarEscala(){
        if(aeroporto != null){
            if(!objeto.getEscalas().contains(aeroporto)){
                objeto.getEscalas().add(aeroporto);
                Util.mensagemInformacao("Escala adicionada com sucesso!");
            }else{
                Util.mensagemErro("Escala já existente na lista de escalas!");
            }
            
        }
    }
    
    public void imprimir(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("Voos", parametros, dao.getListaObjetos());
    }
    
    public void removerEscala(int index){
        objeto.getEscalas().remove(index);
        Util.mensagemInformacao("Escala removida com sucesso");
    }
    
    public void novoVooAgendado(){
        vooAgendado = new VooAgendado();
        novoVooAgendado = true;
    }
    
    public void alterarVooAgendado(int index){
        vooAgendado = objeto.getVoos_agendados().get(index);
        novoVooAgendado = false;
    }
    
    public void salvarVooAgendado(){
        if(novoVooAgendado){
            objeto.adicionarVooAgendado(vooAgendado);
        }
        Util.mensagemInformacao("Voo agendado com sucesso!");
    }
    
    public void removerVooAgendado(int index){
        objeto.removerVooAgendado(index);
        Util.mensagemInformacao("Voo agendado removido com sucesso!");
    }
    
    public String listar(){
        return "/privado/voo/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Voo();
    }
    
    public void salvar(){
        boolean persistiu;
        if(objeto.getId() == null){
            persistiu = dao.persist(objeto);
        }else{
            persistiu = dao.merge(objeto);
        }
        if(persistiu){
            Util.mensagemInformacao(dao.getMensagem());
        }else{
            Util.mensagemErro(dao.getMensagem());
        }
                
    }
    
    public void editar(Integer id){
        objeto = dao.localizar(id);
    }
    
    public void remover(Integer id){
        objeto = dao.localizar(id);
        if(dao.remover(objeto)){
            Util.mensagemInformacao(dao.getMensagem());
        }else{
            Util.mensagemErro(dao.getMensagem());
        }
    }

    public VooDAO getDao() {
        return dao;
    }

    public void setDao(VooDAO dao) {
        this.dao = dao;
    }

    public Voo getObjeto() {
        return objeto;
    }

    public void setObjeto(Voo objeto) {
        this.objeto = objeto;
    }

    public VooAgendado getVooAgendado() {
        return vooAgendado;
    }

    public void setVooAgendado(VooAgendado vooAgendado) {
        this.vooAgendado = vooAgendado;
    }

    public Boolean getNovoVooAgendado() {
        return novoVooAgendado;
    }

    public void setNovoVooAgendado(Boolean novoVooAgendado) {
        this.novoVooAgendado = novoVooAgendado;
    }

    public Aeroporto getAeroporto() {
        return aeroporto;
    }

    public void setAeroporto(Aeroporto aeroporto) {
        this.aeroporto = aeroporto;
    }

    public AeroportoDAO getDaoAeroporto() {
        return daoAeroporto;
    }

    public void setDaoAeroporto(AeroportoDAO daoAeroporto) {
        this.daoAeroporto = daoAeroporto;
    }
    
    
}
