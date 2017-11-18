package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.util.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Leticia-PC
 */
public class DAOGenerico<T> {
    private List<T> listaObjetos;
    private List<T> listaTodos;
    protected EntityManager em;
    protected Class classPersistence;
    protected String mensagem = "";
    protected String ordem = "id";
    protected String filtro = "";
    protected Integer maximoObjetos = 3;
    protected Integer posicaoAtual = 0;
    protected Integer totalObjetos = 0;

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public DAOGenerico() {
       em =EntityManagerUtil.getEntityManager();
    }    
    
    public List<T> getListaObjetos() {
        String jpql = "from " + classPersistence.getSimpleName();
        String where = "";
        //remove ['-;] para proteger de injeção de SQL
        filtro = filtro.replaceAll("[-;]'", "");
        if(filtro.length() > 0){
            if(ordem.equals("id")){
                try{
                    Integer.parseInt(filtro);
                    where+=" where " + ordem + " = '" + filtro + "' ";
                }catch(Exception e){
                    
                }
            }else{
                where+= " where upper(" + ordem + " ) like '%" + filtro.toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        System.out.println("JPQL: " + jpql);
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).setFirstResult(posicaoAtual).setMaxResults(maximoObjetos).getResultList();
    }

    public void setListaObjetos(List<T> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public void primeiro(){
        posicaoAtual = 0;
    }
    
    public void anterior(){
        posicaoAtual -= totalObjetos;
        if(posicaoAtual < 0){
            posicaoAtual = 0;
        }
    }
    
    public void proximo(){
        if(posicaoAtual + maximoObjetos < totalObjetos){
            posicaoAtual = maximoObjetos;
        }
    }
    
    public void ultimo(){
        int resto = totalObjetos % maximoObjetos;
        if(resto > 0){
            posicaoAtual = totalObjetos - resto;
        }else{
            posicaoAtual = totalObjetos - maximoObjetos;
        }
    }

    public String getMensagemNegacao(){
        int ate = posicaoAtual + maximoObjetos;
        if(ate>totalObjetos){
            ate = totalObjetos;
        }
        return "Listagem de " + (posicaoAtual + 1) + " até " + ate + " de " + totalObjetos + " registros";
    }
    public List<T> getListaTodos() {
        String jpql = "from " + classPersistence.getSimpleName() + " order by " + ordem;
        return em.createQuery(jpql).getResultList();
    }

    public void rollback(){
        if(em.getTransaction().isActive() == false){
            em.getTransaction().begin();
        }
        em.getTransaction().rollback();
    }
    
    public boolean persist(T obj){
        try{
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso";
            return true;
        }catch(Exception e){
            rollback();
            mensagem = "Erro ao persistir objeto " + Util.getMensagemErro(e);
            return false;
        }
    }
    
    public boolean merge(T obj){
        try{
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso";
            return true;
        }catch(Exception e){
            rollback();
            mensagem = "Erro ao persistir objeto " + Util.getMensagemErro(e);
            return false;
        }
    }
    
    public boolean remover(T obj){
        try{
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
            mensagem = "Objeto removido com sucesso";
            return true;
        }catch(Exception e){
            rollback();
            mensagem = "Erro ao removido objeto " + Util.getMensagemErro(e);
            return false;
        }
    }
    
    public T localizar(Object id){
        rollback();
        T obj = (T) em.find(classPersistence, id);
        return obj;
    }
    
    public void setListaTodos(List<T> listaTodos) {
        this.listaTodos = listaTodos;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassPersistence() {
        return classPersistence;
    }

    public void setClassPersistence(Class classPersistence) {
        this.classPersistence = classPersistence;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
