package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

    private static String banco = "jdbc:postgresql://localhost:5432/curso_jsp?autoReconnect=true";
    private static String user = "postgres";
    private static String senha = "12345";
    private static Connection connection = null;

    public static Connection getConnection() {
        return connection;
    }

    static {
        conectar();
    }
    
    public SingleConnectionBanco (){ //qd tiver uma instancia vai conectar
        conectar();
    }

    private static void conectar() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver"); // carrega o driver de conexao com o bco
                connection = DriverManager.getConnection(banco, user, senha);
                connection.setAutoCommit(false); // para n efetuar alteracoes no bco sem nosso comando
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}