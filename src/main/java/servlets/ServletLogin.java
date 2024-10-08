package servlets;
import dao.DAOLoginRepository;
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

@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"})
public class ServletLogin extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;

    private final DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
    private final DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletLogin () {

    }

    // recebe os dados pela url em parametros
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao != null && acao.equalsIgnoreCase("logout")) {
            request.getSession().invalidate(); // invalida a sessão
            RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
            redirect.forward(request, response);
        } else {
            doPost(request, response);
        }
    }

    // recebe os dados enviados por um formulario
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String url = request.getParameter("url");

        try {

            ModelLogin modelLogin = new ModelLogin();

            if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
                modelLogin.setLogin(login);
                modelLogin.setSenha(senha);
                if (daoLoginRepository.validarAutenticacao(modelLogin)) {/*simulando login*/

                    modelLogin = daoUsuarioRepository.consultaUsuarioLogado(login);
                    request.getSession().setAttribute("usuario", modelLogin.getLogin());
                    request.getSession().setAttribute("perfil", modelLogin.getPerfil());

                    if (url == null || url.equals("null")) {
                        url = "principal/principal.jsp";
                    }

                    RequestDispatcher redirect = request.getRequestDispatcher(url);
                    redirect.forward(request, response);
                } else {
                    RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp");
                    request.setAttribute("msg", "Informe o login e senha corretamente");
                    redirect.forward(request, response);
                }
            } else {
                RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
                request.setAttribute("msg", "Informe o login e senha corretamente");
                redirect.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirect = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirect.forward(request, response);
        }

    }

}
