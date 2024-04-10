package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.io.Serializable;

@WebServlet(name = "ServletUsuarioController", urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;

    public ServletUsuarioController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/usuario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        request.setAttribute("modelLogin", modelLogin);

        RequestDispatcher redirect = request.getRequestDispatcher("principal/usuario.jsp");
        redirect.forward(request, response);
    }
}
