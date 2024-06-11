package dao;

import connection.SingleConnectionBanco;
import model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUsuarioRepository {

    private final Connection connection;

    public DAOUsuarioRepository() {
        connection = SingleConnectionBanco.getConnection();
    }

    public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {
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

        return this.consultaUsuario(objeto.getLogin());
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


}
