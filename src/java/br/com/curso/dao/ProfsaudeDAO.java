package br.com.curso.dao;

import br.com.curso.model.Profsaude;
import br.com.curso.model.Cidade;
import br.com.curso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProfsaudeDAO implements GenericDAO {
    
    private Connection conexao;
    
    public ProfsaudeDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Boolean retorno = false;
        try {
            Profsaude oProfsaude = (Profsaude) objeto;
            if (oProfsaude.getIdProfsaude()==0) { //inserção
                //verifica se já existe pessoa com este CPF cadastrada.
                int idProfsaude = this.verificarCpf(oProfsaude.getCpfCnpj());
                if (idProfsaude==0) {
                    //se não encontrou insere
                    retorno = this.inserir(oProfsaude);
                }else{
                    //se encontrou profsaude com o cpf altera
                    oProfsaude.setIdProfsaude(idProfsaude);
                    retorno = this.alterar(oProfsaude);
                }
            } else {
              retorno = this.alterar(oProfsaude);
            }
        } catch (Exception ex){
            System.out.println("Problemas ao incluir profsaude! Erro "+ex.getMessage());            
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Profsaude oProfsaude = (Profsaude) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into profsaude (idPessoa, permitelogin, situacao, cartprof)"
                + " values (?, ?, ?, ?)";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            //manda informações para o cadastrar de pessoa.
            int idPessoa = oPessoaDAO.cadastrar(oProfsaude);
            stmt = conexao.prepareStatement(sql);
            System.out.println(oProfsaude.getPermiteLogin());
            System.out.println(oProfsaude.getSituacao());
            System.out.println(oProfsaude.getCartprof());
            stmt.setInt(1, idPessoa);
            stmt.setString(2, oProfsaude.getPermiteLogin());
            stmt.setString(3, "A");
            stmt.setString(4, oProfsaude.getCartprof());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception e){
            try {
                System.out.println("Problemas ao cadastrar Profsaude!Erro: " + e.getMessage());
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
        Profsaude oProfsaude = (Profsaude) objeto;
        PreparedStatement stmt = null;
        String sql = "update profsaude set permitelogin=?, cartprof=? where idProfsaude=?";
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            oPessoaDAO.cadastrar(oProfsaude); //envia para classe PessoaDAO
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oProfsaude.getPermiteLogin());
            stmt.setString(2, oProfsaude.getCartprof());
            stmt.setInt(3, oProfsaude.getIdProfsaude());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception e){
            try {
                System.out.println("Problemas ao alterar Profsaude!Erro: " + e.getMessage());
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
            //carrega dados de profsaude
            ProfsaudeDAO oProfsaudeDAO = new ProfsaudeDAO();
            Profsaude oProfsaude = (Profsaude) oProfsaudeDAO.carregar(numero);
            String situacao="A";//verifica e troca a situação do profsaude
            if(oProfsaude.getSituacao().equals(situacao))
                situacao = "I";
            else situacao = "A";
            
            String sql = "update profsaude set situacao=? where idProfsaude=?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, situacao);
            stmt.setInt(2, oProfsaude.getIdProfsaude());
            stmt.execute();
            conexao.commit();
            return true;
        }catch (Exception e){
            try {
                System.out.println("Problemas ao excluir Profsaude!Erro: " + e.getMessage());
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
        int idProfsaude = numero;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Profsaude oProfsaude = null;
        String sql = "Select * from profsaude c, pessoa p "
                + "where c.idpessoa = p.idpessoa and c.idprofsaude=?";
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setInt(1, idProfsaude);
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
                oProfsaude = new Profsaude(rs.getInt("idprofsaude"),   
                                       rs.getString("permitelogin"),
                                       rs.getString("situacao"),
                                       rs.getString("cartprof"),
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
            System.out.println("Problemas ao carregar Profsaude!Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return oProfsaude;
    }

    @Override
    
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql= "Select p.*, c.idprofsaude, c.cartprof, c.situacao, c.permitelogin "
                + "from profsaude c, pessoa p "
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
                Profsaude oProfsaude = new Profsaude(rs.getInt("idprofsaude"),
                                       rs.getString("permitelogin"),
                                       rs.getString("situacao"),
                                       rs.getString("cartprof"),
                                       rs.getInt("idpessoa"),
                                       rs.getString("cpfcnpj"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade,
                                       rs.getString("login"),
                                       rs.getString("senha"),
                                       rs.getString("foto"));
                resultado.add(oProfsaude);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar profsaude! Erro "+ex.getMessage());
        }
        return resultado;
    }

    public List<Profsaude> listar(String nomeProfissional) throws ParseException{
        List<Profsaude> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from profsaude pf inner join pessoa pe on pf.idpessoa = pe.idpessoa where nome like ?";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomeProfissional+"%");
            rs = stmt.executeQuery();
            while(rs.next()){
                Profsaude oProfsaude = Profsaude.profsaudeVazio();
                oProfsaude.setIdProfsaude(rs.getInt("idprofsaude"));
                oProfsaude.setNome(rs.getString("nome"));
                resultado.add(oProfsaude);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao Consultar Profissional! Erro: "+ex.getMessage());
        }
        return resultado;
    }
    
    public int verificarCpf(String cpf){
        PreparedStatement stmt = null;
        ResultSet rs= null;
        int idProfsaude = 0;
        String sql = "Select c.* from profsaude c, pessoa p "
                + "where c.idpessoa = p.idPessoa and p.cpfcnpj=?;";
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            while(rs.next()){
                idProfsaude = rs.getInt("idprofsaude");
            }
            return idProfsaude;
        }catch(SQLException ex){
            System.out.println("Problemas ai carregar pessoa! Erro: "+ex.getMessage());
            return idProfsaude;
        }
    }   
}
