/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.curso.model;

import br.com.curso.model.Cidade;
import br.com.curso.model.Pessoa;
import br.com.curso.utils.Conversao;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Aluno
 */
public class Paciente extends Pessoa{
    private int idPaciente;
    private String carteirinha;
    private String situacao;
    private String permiteLogin;

    public Paciente(int idPaciente, String permiteLogin, String situacao, String carteirinha, int idPessoa, 
            String cpfCnpj, String nome, Date dataNascimento, Cidade cidade, String login, 
            String senha, String foto) {
        super(idPessoa, cpfCnpj, nome, dataNascimento, cidade, login, senha, foto);
        this.idPaciente = idPaciente;
        this.carteirinha = carteirinha;
        this.situacao = situacao;
        this.permiteLogin = permiteLogin;
    }
    public static Paciente pacienteVazio() throws ParseException{
        Cidade oCidade = new Cidade();
        Date dataNascimento = Conversao.dataAtual();
        Paciente oPaciente = new Paciente(0,"S","A","",0,"","",dataNascimento,oCidade,"","",null);
        return oPaciente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCarteirinha() {
        return carteirinha;
    }

    public void setCarteirinha(String carteirinha) {
        this.carteirinha = carteirinha;
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
