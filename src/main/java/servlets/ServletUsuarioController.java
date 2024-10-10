package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@MultipartConfig()
@WebServlet(name = "ServletUsuarioController", urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletUsuarioController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String acao = request.getParameter("acao");
            if (acao != null && acao.equalsIgnoreCase("deletar")) {
                String idUser = request.getParameter("id");

                List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
                request.setAttribute("modelLogins", modelLogins);

                daoUsuarioRepository.deletarUsuario(idUser);
                request.setAttribute("msg", "Usuário deletado com sucesso!");
            } else if(acao != null && acao.equalsIgnoreCase("deletarajax")) {
                String idUser = request.getParameter("id");

                daoUsuarioRepository.deletarUsuario(idUser);

                response.getWriter().write("Usuário deletado com sucesso!");

            } else if(acao != null && acao.equalsIgnoreCase("pesquisarUserAjax")) {
                String nomePesquisa = request.getParameter("nomePesquisar");
                
                List<ModelLogin> dadosJsonUserList = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
                
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(dadosJsonUserList);
                response.getWriter().write(jsonString);

            } else if (acao != null && acao.equalsIgnoreCase("pesquisarEditar")) {
                String id = request.getParameter("id");

                ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioporId(id, super.getUserLogado(request));

                List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
                request.setAttribute("modelLogins", modelLogins);

                request.setAttribute("msg", "Usuário em Edição");
                request.setAttribute("modelLogin", modelLogin);
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

            } else if (acao != null && acao.equalsIgnoreCase("listarUser")) {

                List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));

                request.setAttribute("msg", "Usuários carregados");
                request.setAttribute("modelLogins", modelLogins);
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }
            else {
                List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
                request.setAttribute("modelLogins", modelLogins);

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
            String perfil = request.getParameter("perfil");
            String sexo = request.getParameter("sexo");

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
            modelLogin.setNome(nome);
            modelLogin.setEmail(email);
            modelLogin.setLogin(login);
            modelLogin.setSenha(senha);
            modelLogin.setPerfil(perfil);
            modelLogin.setSexo(sexo);

            if (request.getContentType() != null && request.getContentType().startsWith("multipart/")) {
                Part filePart = request.getPart("fileFoto"); // pega foto da tela
                byte[] foto = IOUtils.toByteArray(filePart.getInputStream()); // converte a imagem para byte
                String imagemBase64 = "data:image/" + filePart.getContentType().split("\\/")[1]+ ";base64," + new Base64().encodeBase64String(foto);

                modelLogin.setFotoUser(imagemBase64);
                modelLogin.setExtensaoFotoUser(filePart.getContentType().split("\\/")[1]);
            }
    
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
            modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
        }

        List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
        request.setAttribute("modelLogins", modelLogins);

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
