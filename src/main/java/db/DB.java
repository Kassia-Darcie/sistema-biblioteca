package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    /** Método para fazer a conexão com o DB, se ainda não existir uma conexão utiliza o método getConnection da classe
     * DriveManager do jdbc para obter a conexão
     * @return Connection - a conexão com o banco de dados
     * @throws DbException - Exceção personalizada para lidar com erros relativos ao DB*/
    public static  Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    /**Fecha a conexão com o banco de dados*/
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /** Método para carregar as propriedades de conexão do banco de dados do arquivo db.properties
     * @return Properties - propriedades carregadas*/
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
