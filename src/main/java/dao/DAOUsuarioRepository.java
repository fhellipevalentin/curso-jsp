package dao;

import connection.SingleConnectionBanco;
import model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarioRepository {

    private final Connection connection;

    public DAOUsuarioRepository() {
        connection = SingleConnectionBanco.getConnection();
    }

    public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

        if (objeto.isNovo()) {

            String sql = "INSERT INTO model_login (login, nome, email, senha, usuario_id, perfil, sexo) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            try {
                preparedStatement.setString(1, objeto.getLogin());
                preparedStatement.setString(2, objeto.getNome());
                preparedStatement.setString(3, objeto.getEmail());
                preparedStatement.setString(4, objeto.getSenha());
                preparedStatement.setLong(5, userLogado);
                preparedStatement.setString(6, objeto.getPerfil());
                preparedStatement.setString(7, objeto.getSexo());
            } catch (Exception e) {
                connection.rollback();
            }
            preparedStatement.execute();
            connection.commit();

            if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
                sql = "update model_login set fotouser = ?, extensaofotouser=? where login = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, objeto.getFotoUser());
                preparedStatement.setString(2, objeto.getExtensaoFotoUser());
                preparedStatement.setString(3, objeto.getLogin());

                preparedStatement.execute();
                connection.commit();
            }

        } else {
            String sql = "UPDATE model_login SET login=?, nome=?, email=?, senha=?, perfil=?, sexo=?  WHERE id="+objeto.getId()+";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            try {
                preparedStatement.setString(1, objeto.getLogin());
                preparedStatement.setString(2, objeto.getNome());
                preparedStatement.setString(3, objeto.getEmail());
                preparedStatement.setString(4, objeto.getSenha());
                preparedStatement.setString(5, objeto.getPerfil());
                preparedStatement.setString(6, objeto.getSexo());
            } catch (Exception e) {
                connection.rollback();
            }
            preparedStatement.executeUpdate();
            connection.commit();

            if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
                sql = "update model_login set fotouser = ?, extensaofotouser=? where id = ?";
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, objeto.getFotoUser());
                preparedStatement.setString(2, objeto.getExtensaoFotoUser());
                preparedStatement.setLong(3, objeto.getId());

                preparedStatement.execute();
                connection.commit();
            }
        }
        return this.consultaUsuario(objeto.getLogin(), userLogado);
    }

    public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException {
        List<ModelLogin> retorno = new ArrayList<>();
        String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while(resultado.next()) { //percorre as linhas de resultado do SQL
            model.ModelLogin modelLogin = new model.ModelLogin();
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setId(resultado.getLong("id"));
            modelLogin.setNome(resultado.getString("nome"));
            //modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setPerfil(resultado.getString("perfil"));
            modelLogin.setSexo(resultado.getString("sexo"));

            retorno.add(modelLogin);
        }

        return retorno;
    }

    public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws SQLException {
    	List<ModelLogin> retorno = new ArrayList<ModelLogin>();
    	String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";
    	PreparedStatement statement = connection.prepareStatement(sql);
    	statement.setString(1, "%"+nome+"%");
    	statement.setLong(2, userLogado);

    	ResultSet resultado = statement.executeQuery();
    	
    	while(resultado.next()) { //percorre as linhas de resultado do SQL
    		ModelLogin modelLogin = new ModelLogin();
    		modelLogin.setEmail(resultado.getString("email"));
    		modelLogin.setId(resultado.getLong("id"));
    		modelLogin.setNome(resultado.getString("nome"));
    		//modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setPerfil(resultado.getString("perfil"));
            modelLogin.setSexo(resultado.getString("sexo"));
    		retorno.add(modelLogin);
    	}
    	
    	return retorno;
    }

    public ModelLogin consultaUsuarioporId(String id, Long userLogado) throws Exception {
        ModelLogin modelLogin = new ModelLogin();

        String sql = "SELECT * FROM model_login WHERE id = ? and useradmin is false and usuario_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, Long.parseLong(id));
        statement.setLong(2, userLogado);

        ResultSet resultado = statement.executeQuery();

        if (resultado.next()) {
            modelLogin.setId(resultado.getLong("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));
            modelLogin.setSexo(resultado.getString("sexo"));
            modelLogin.setFotoUser(resultado.getString("fotoUser"));
        }
        return modelLogin;
    }

    public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
        ModelLogin modelLogin = new ModelLogin();

        String sql = "SELECT * FROM model_login WHERE login = ? and useradmin is false and usuario_id = " + userLogado;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);

        ResultSet resultado = statement.executeQuery();
        
        if (resultado.next()) {
            modelLogin.setId(resultado.getLong("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));
            modelLogin.setSexo(resultado.getString("sexo"));
            modelLogin.setFotoUser(resultado.getString("fotoUser"));
        }
        return modelLogin;
    }

    public ModelLogin consultaUsuario(String login) throws Exception {
        ModelLogin modelLogin = new ModelLogin();

        String sql = "SELECT * FROM model_login WHERE login = ? and useradmin is false";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);

        ResultSet resultado = statement.executeQuery();

        if (resultado.next()) {
            modelLogin.setId(resultado.getLong("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
            modelLogin.setPerfil(resultado.getString("perfil"));
            modelLogin.setSexo(resultado.getString("sexo"));
            modelLogin.setFotoUser(resultado.getString("fotoUser"));
        }
        return modelLogin;
    }

    public ModelLogin consultaUsuarioLogado(String login) throws Exception {
        ModelLogin modelLogin = new ModelLogin();

        String sql = "SELECT * FROM model_login WHERE upper(login) = upper('"+login+"')";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        if (resultado.next()) {
            modelLogin.setId(resultado.getLong("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
            modelLogin.setPerfil(resultado.getString("perfil"));
            modelLogin.setSexo(resultado.getString("sexo"));
            modelLogin.setFotoUser(resultado.getString("fotouser"));
            modelLogin.setExtensaoFotoUser(resultado.getString("extensaofotouser"));
        }
        return modelLogin;
    }

    public boolean validarLogin(String login) throws Exception {
        String sql = "SELECT count(1) > 0 as existe FROM model_login WHERE upper(login) = upper('"+login+"');";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();
        resultado.next();
        return resultado.getBoolean("existe");

    }

    public void deletarUsuario(String idUser) throws Exception{
        String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong( 1, Long.parseLong(idUser));

        preparedStatement.executeUpdate();

        connection.commit();
    }

}
