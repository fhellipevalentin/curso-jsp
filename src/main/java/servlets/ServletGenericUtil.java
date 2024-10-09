package servlets;


import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;

public class ServletGenericUtil extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;

    private final DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public Long getUserLogado(HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();

        String usuarioLogado = (String) session.getAttribute("usuario");

        return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado).getId();
    }
}
