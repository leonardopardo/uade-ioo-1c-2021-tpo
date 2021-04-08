package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private String url;

    private Connection conn;

    public ConnectionService() throws SQLException {
        this.url = "src\\database\\database.sqlite";
    }

    public ConnectionService(String url) throws SQLException {
        this.url = url;
    }

    public void disconnect() throws SQLException {
        this.conn.close();
    }

    public Connection connect() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.url);

        if(this.conn == null)
            throw new SQLException();

        return this.conn;
    }
}
