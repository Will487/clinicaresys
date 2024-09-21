package br.com.curso.dao;

import br.com.curso.model.Paciente;
import br.com.curso.model.Cidade;
import br.com.curso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO implements GenericDAO {
    
    private Connection conexao;
    
    public PacienteDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Boolean retorno = false;
        try {
            Paciente oPaciente = (Paciente) objeto;
            if (oPaciente.getIdPaciente()==0) { //inserção
                //verifica se já existe pessoa com este CPF cadastrada.
                int idPaciente = this.verificarCpf(oPaciente.getCpfCnpj());
                if (idPaciente==0) {
                    //se não encontrou insere
                    retorno = this.inserir(oPaciente);
                }else{
                    //se encontrou paciente com o cpf altera
                    oPaciente.setIdPaciente(idPaciente);
                    retorno = this.alterar(oPaciente);
                }
            } else {
              retorno = this.alterar(oPaciente);
            }
        } catch (Exception ex){
            System.out.println("Problemas ao incluir paciente! Erro "+ex.getMessage());            
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Paciente oPaciente = (Paciente) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into paciente (idPessoa, permitelogin, situacao, carteirinha)"
                + " values (?, ?, ?, ?)";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            //manda informações para o cadastrar de pessoa.
            int idPessoa = oPessoaDAO.cadastrar(oPaciente);
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            stmt.setString(2, oPaciente.getPermiteLogin());
            stmt.setString(3, "A");
            stmt.setString(4, oPaciente.getCarteirinha());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception e){
            try {
                System.out.println("Problemas ao cadastrar Paciente!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback(); 
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Paciente oPaciente = (Paciente) objeto;
        PreparedStatement stmt = null;
        String sql = "update paciente set permitelogin=?, carteirinha=? where idPaciente=?";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            oPessoaDAO.cadastrar(oPaciente); //envia para classe PessoaDAO
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPaciente.getPermiteLogin());
            stmt.setString(2, oPaciente.getCarteirinha());
            stmt.setInt(3, oPaciente.getIdPaciente());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception e){
            try {
                System.out.println("Problemas ao alterar Paciente!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback(); 
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {
        PreparedStatement stmt = null;
        try{
            //carrega dados de paciente
            PacienteDAO oPacienteDAO = new PacienteDAO();
            Paciente oPaciente = (Paciente) oPacienteDAO.carregar(numero);
            String situacao="A";//verifica e troca a situação do paciente
            if(oPaciente.getSituacao().equals(situacao))
                situacao = "I";
            else situacao = "A";
            
            String sql = "update paciente set situacao=? where idPaciente=?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, situacao);
            stmt.setInt(2, oPaciente.getIdPaciente());
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception e){
            try {
                System.out.println("Problemas ao excluir Paciente!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback(); 
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
    }
    @Override
    public Object carregar(int numero) {
        int idPaciente = numero;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Paciente oPaciente = null;
        String sql = "Select * from paciente c, pessoa p "
                + "where c.idpessoa = p.idpessoa and c.idpaciente=?";
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setInt(1, idPaciente);
            rs=stmt.executeQuery();
            while(rs.next()){
                //Busca a cidade
                Cidade oCidade = null;
                try{
                   CidadeDAO oCidadeDAO = new CidadeDAO();
                   oCidade = (Cidade) oCidadeDAO.carregar(rs.getInt("idcidade"));
                }catch(Exception ex){
                   System.out.println("Problemas ao carregar cidade!Erro:"+ex.getMessage());
                }
                oPaciente = new Paciente(rs.getInt("idpaciente"),   
                                       rs.getString("permitelogin"),
                                       rs.getString("situacao"),
                                       rs.getString("carteirinha"),
                                       rs.getInt("idpessoa"),
                                       rs.getString("cpfcnpj"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade,
                                       rs.getString("login"),
                                       rs.getString("senha"),
                                       rs.getString("foto"));
            }
        }catch(SQLException e){
            System.out.println("Problemas ao carregar Paciente!Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return oPaciente;
    }

    @Override
    
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql= "Select p.*, c.idpaciente, c.carteirinha, c.situacao, c.permitelogin "
                + "from paciente c, pessoa p "
                + "where c.idpessoa = p.idpessoa order by idPessoa";
        try{
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()){
                Cidade oCidade = null;//busca cidade
                try{
                   CidadeDAO oCidadeDAO = new CidadeDAO();
                   oCidade = (Cidade) oCidadeDAO.carregar(rs.getInt("idcidade"));
                }catch(Exception ex){
                   System.out.println("Problemas ao carregar usuario!Erro:"+ex.getMessage());
                }
                Paciente oPaciente = new Paciente(rs.getInt("idpaciente"),
                                       rs.getString("permitelogin"),
                                       rs.getString("situacao"),
                                       rs.getString("carteirinha"),
                                       rs.getInt("idpessoa"),
                                       rs.getString("cpfcnpj"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade,
                                       rs.getString("login"),
                                       rs.getString("senha"),
                                       rs.getString("foto"));
                resultado.add(oPaciente);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar paciente! Erro "+ex.getMessage());
        }
        return resultado;
    }
    
    public List<Paciente> listar(String nomePaciente) throws ParseException{
        List<Paciente> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from paciente pa inner join pessoa pe on pa.idpessoa = pe.idpessoa where nome like ?";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomePaciente+"%");
            rs = stmt.executeQuery();
            while(rs.next()){
                Paciente oPaciente = Paciente.pacienteVazio();
                oPaciente.setIdPaciente(rs.getInt("idpaciente"));
                oPaciente.setNome(rs.getString("nome"));
                resultado.add(oPaciente);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao Consultar Paciente! Erro: "+ex.getMessage());
        }
        return resultado;
    }
    
    public int verificarCpf(String cpf){
        PreparedStatement stmt = null;
        ResultSet rs= null;
        int idPaciente = 0;
        String sql = "Select c.* from paciente c, pessoa p "
                + "where c.idpessoa = p.idPessoa and p.cpfcnpj=?;";
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            while(rs.next()){
                idPaciente = rs.getInt("idpaciente");
            }
            return idPaciente;
        }catch(SQLException ex){
            System.out.println("Problemas ai carregar pessoa! Erro: "+ex.getMessage());
            return idPaciente;
        }
    }   
}
