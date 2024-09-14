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

    public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {

        if (objeto.isNovo()) {

            String sql = "INSERT INTO model_login (login, nome, email, senha) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            try {
                preparedStatement.setString(1, objeto.getLogin());
                preparedStatement.setString(2, objeto.getNome());
                preparedStatement.setString(3, objeto.getEmail());
                preparedStatement.setString(4, objeto.getSenha());
            } catch (Exception e) {
                connection.rollback();
            }
            preparedStatement.execute();
            connection.commit();
        } else {
            String sql = "UPDATE model_login SET login=?, nome=?, email=?, senha=? WHERE id="+objeto.getId()+";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            try {
                preparedStatement.setString(1, objeto.getLogin());
                preparedStatement.setString(2, objeto.getNome());
                preparedStatement.setString(3, objeto.getEmail());
                preparedStatement.setString(4, objeto.getSenha());
            } catch (Exception e) {
                connection.rollback();
            }
            preparedStatement.executeUpdate();
            connection.commit();
        }
        return this.consultaUsuario(objeto.getLogin());
    }
    
    public List<ModelLogin> consultaUsuarioList(String nome) throws SQLException {
    	List<ModelLogin> retorno = new ArrayList<ModelLogin>();
    	String sql = "select * from model_login where upper(nome) like upper(?)";
    	PreparedStatement statement = connection.prepareStatement(sql);
    	statement.setString(1, "%"+nome+"%");
    	
    	ResultSet resultado = statement.executeQuery();
    	
    	while(resultado.next()) { //percorre as linhas de resultado do SQL
    		ModelLogin modelLogin = new ModelLogin();
    		modelLogin.setEmail(resultado.getString("email"));
    		modelLogin.setId(resultado.getLong("id"));
    		modelLogin.setNome(resultado.getString("nome"));
    		//modelLogin.setSenha(resultado.getString("senha"));
    		
    		retorno.add(modelLogin);
    	}
    	
    	return retorno;
    }
    
    public ModelLogin consultaUsuario(String login) throws Exception {
        ModelLogin modelLogin = new ModelLogin();

        String sql = "SELECT * FROM model_login WHERE login = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);

        ResultSet resultado = statement.executeQuery();
        
        if (resultado.next()) {
            modelLogin.setId(resultado.getLong("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
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
        String sql = "DELETE FROM model_login WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong( 1, Long.parseLong(idUser));

        preparedStatement.executeUpdate();

        connection.commit();
    }

}
