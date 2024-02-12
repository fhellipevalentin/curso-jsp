package servlets;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {

    public ServletLogin () {

    }

    // recebe os dados pela url em parametros
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // recebe os dados enviados por um formulario
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        ModelLogin modelLogin =new ModelLogin();

        if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
            modelLogin.setLogin(login);
            modelLogin.setSenha(senha);
            if(modelLogin.getLogin().equalsIgnoreCase("admin")
                    && modelLogin.getSenha().equalsIgnoreCase("admin")){/*simulando login*/
                request.getSession().setAttribute("usuario", modelLogin.getLogin());
                RequestDispatcher redirect = request.getRequestDispatcher("principal/principal.jsp");
                redirect.forward(request, response);
            } else {
                RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
                request.setAttribute("msg", "Informe o login e senha corretamente");
                redirect.forward(request, response);
            }
        } else {
            RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
            request.setAttribute("msg", "Informe o login e senha corretamente");
            redirect.forward(request, response);
        }

    }

}
