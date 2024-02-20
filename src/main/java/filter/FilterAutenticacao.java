package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/principal/*") // Intercepta todas as requisicoes que vierem do projeto ou mapeamento
public class FilterAutenticacao  implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req =  (HttpServletRequest) request;
        HttpSession sessao = req.getSession();
        
        String usuarioLogado = (String) sessao.getAttribute("usuario");

        String urlParaAutenticar = req.getServletPath(); // url q esta sendo acessada

        // valida se o usuario esta logado, senao, redireiona para a url de login
        if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { // não está logado
            RequestDispatcher redir = req.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
            request.setAttribute("msg", "Por favor, faça login para continuar");
            redir.forward(request,response);
            return; // para a execucao e redireciona para o login
        } else {
            chain.doFilter(request, response);
        }
        
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
