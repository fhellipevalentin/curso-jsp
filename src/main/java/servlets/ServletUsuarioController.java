package servlets;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "ServletUsuarioController", urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;

    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public DAOUsuarioRepository getDaoUsuarioRepository() {
        return daoUsuarioRepository;
    }

    public ServletUsuarioController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String acao = request.getParameter("acao");
            if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
                String idUser = request.getParameter("id");

                daoUsuarioRepository.deletarUsuario(idUser);
                request.setAttribute("msg", "Usuário deletado com sucesso!");
            } else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
                String idUser = request.getParameter("id");

                daoUsuarioRepository.deletarUsuario(idUser);

                response.getWriter().write("Usuário deletado com sucesso!");

            } else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pesquisarUserAjax")) {
                String nomePesquisa = request.getParameter("nomePesquisar");
                
                List<ModelLogin> dadosJsonUserList = daoUsuarioRepository.consultaUsuarioList(nomePesquisa);
                
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(dadosJsonUserList);
                response.getWriter().write(jsonString);

            } else {
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirect = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirect.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        try {



            String id = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
            modelLogin.setNome(nome);
            modelLogin.setEmail(email);
            modelLogin.setLogin(login);
            modelLogin.setSenha(senha);
    
        if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
            request.setAttribute("msg","Nome de usuário já está em uso, tente outro.");
            request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            return;
        } else {
            if (modelLogin.isNovo()) {
                msg = "Salvo com sucesso!";
            } else {
                msg = "Atualizado com sucesso!";
            }
            modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);
        }

        request.setAttribute("msg", msg);
        request.setAttribute("modelLogin", modelLogin);
        request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);


        } catch (SQLException e) {
            e.printStackTrace();
            RequestDispatcher redirect = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirect.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
