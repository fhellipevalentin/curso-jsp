package filter;

import connection.SingleConnectionBanco;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = "/principal/*") // Intercepta todas as requisicoes que vierem do projeto ou mapeamento
public class FilterAutenticacao  implements Filter {

    private static Connection connection;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        connection = SingleConnectionBanco.getConnection(); // inicia a conexao ao subir o projeto
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession sessao = req.getSession();

            String usuarioLogado = (String) sessao.getAttribute("usuario");

            String urlParaAutenticar = req.getServletPath(); // url q esta sendo acessada

            // valida se o usuario esta logado, senao, redireiona para a url de login
            if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { // não está logado
                RequestDispatcher redir = req.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
                request.setAttribute("msg", "Por favor, faça login para continuar");
                redir.forward(request, response);
                return; // para a execucao e redireciona para o login
            } else {
                chain.doFilter(request, response);
            }

            connection.commit(); // faz o commit para que as alteracoes sejam salvas
        } catch (Exception e) {
            e.printStackTrace();
            
            RequestDispatcher redirect = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirect.forward(request, response);
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }

    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Filter.super.destroy();
    }
}
