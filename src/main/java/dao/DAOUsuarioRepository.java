package dao;

import connection.SingleConnectionBanco;
import model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOUsuarioRepository {

    private Connection connection;

    public DAOUsuarioRepository() {
        connection = SingleConnectionBanco.getConnection();
    }

    public void gravarUsuario(ModelLogin objeto) throws SQLException {
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
    }

}
