import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {


    private static Connection getConnection(String path) {
        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+path);
            System.out.println("Connected successfuly!");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Connection Error!");
            System.out.println(throwables.toString());
        }
        return connection;
    }

    static Connection sqliteCon;

    static void setSqliteCon(String path){
        sqliteCon = getConnection(path);
    }

    public static void executeInsert(int id, int nomer_sahifa, String matn) throws SQLException {
        String query = String.format("INSERT INTO kitob_sahifa(id,id_kitob, nomer_sahifa,matn) VALUES ('%s',1,'%s','%s');", id,nomer_sahifa, matn);
        Statement statement = sqliteCon.createStatement();
        statement.executeUpdate(query);

    }

}
