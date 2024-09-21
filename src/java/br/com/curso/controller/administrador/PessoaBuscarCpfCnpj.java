/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.curso.controller.administrador;

import br.com.curso.dao.AdministradorDAO;
import br.com.curso.dao.PacienteDAO;
import br.com.curso.dao.ProfsaudeDAO;
import br.com.curso.dao.PessoaDAO;
import br.com.curso.model.Administrador;
import br.com.curso.model.Paciente;
import br.com.curso.model.Profsaude;
import br.com.curso.model.Pessoa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aluno
 */
@WebServlet(name = "PessoaBuscarCpfCnpj", urlPatterns = {"/PessoaBuscarCpfCnpj"})
public class PessoaBuscarCpfCnpj extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            String cpfCnpj = request.getParameter("cpfcnpjpessoa");
            String tipoPessoa = request.getParameter("tipopessoa");
            int id = 0;
            String JsonRetorno="";
            if (tipoPessoa.equals("administrador")){
                AdministradorDAO oAdmDAO = new AdministradorDAO();
                //busca adm por cpf.
                Administrador oAdm = (Administrador) oAdmDAO.carregar(oAdmDAO.verificarCpf(cpfCnpj));
                //gera retorno
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                JsonRetorno = gson.toJson(oAdm);
            } else if (tipoPessoa.equals("paciente")){
               PacienteDAO oCliDAO = new PacienteDAO();
                //busca adm por cpf.
               Paciente oCli = (Paciente) oCliDAO.carregar(oCliDAO.verificarCpf(cpfCnpj));
                //gera retorno
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                JsonRetorno = gson.toJson(oCli);
            } else if (tipoPessoa.equals("profsaude")){
                ProfsaudeDAO oForDAO = new ProfsaudeDAO();
                //busca adm por cpf.
               Profsaude oFor = (Profsaude) oForDAO.carregar(oForDAO.verificarCpf(cpfCnpj));
                //gera retorno
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                JsonRetorno = gson.toJson(oFor);
            } else {
                //não tem ADM/Paciente ou Forn. -- então verificar Pessoa por CPF
                PessoaDAO oPessoaDAO = new PessoaDAO();
                Pessoa oPessoa = oPessoaDAO.carregarCpf(cpfCnpj);
                
                
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                JsonRetorno = gson.toJson(oPessoa);
            }
            
            response.setCharacterEncoding("iso-8859-1");
            response.getWriter().write(JsonRetorno);
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar pessoa por CPF/CNPJ"
            + "Erro: " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
