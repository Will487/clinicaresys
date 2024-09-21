package br.com.curso.model;

import br.com.curso.utils.Conversao;
import java.text.ParseException;
import java.util.Date;

public class Profsaude extends Pessoa{
private int idProfsaude;
private String cartprof;
private String situacao;
private String permiteLogin;

    public Profsaude(int idProfsaude, String permiteLogin, String situacao, String cartprof, int idPessoa, String cpfCnpj, String nome, Date dataNascimento, Cidade cidade, String login, String senha, String foto) {
        super(idPessoa, cpfCnpj, nome, dataNascimento, cidade, login, senha, foto);
        this.idProfsaude = idProfsaude;
        this.cartprof = cartprof;
        this.situacao = situacao;
        this.permiteLogin = permiteLogin;
    }

    


  public static Profsaude profsaudeVazio() throws ParseException{
        Cidade oCidade = new Cidade();
        Date dataNascimento = Conversao.dataAtual();
        Profsaude oProfsaude = new Profsaude(0,"S","A","",0,"","",dataNascimento,oCidade,"","",null);
        return oProfsaude;
    }

    public int getIdProfsaude() {
        return idProfsaude;
    }

    public void setIdProfsaude(int idProfsaude) {
        this.idProfsaude = idProfsaude;
    }

    public String getCartprof() {
        return cartprof;
    }

    public void setCartprof(String cartprof) {
        this.cartprof = cartprof;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getPermiteLogin() {
        return permiteLogin;
    }

    public void setPermiteLogin(String permiteLogin) {
        this.permiteLogin = permiteLogin;
    }

   
}