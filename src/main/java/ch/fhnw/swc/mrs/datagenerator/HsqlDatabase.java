package ch.fhnw.swc.mrs.datagenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Responsible to initialize database.
 */
public final class HsqlDatabase {
    public static final String DB_DRIVER = "org.hsqldb.jdbcDriver";
    public static final String DB_CONNECTION = "jdbc:hsqldb:hsql://localhost/mrsdb";
    
    private Connection connection;

    /** 
     * Create a connection to a Hsqldb database.
     * @throws Exception whenever something goes wrong.
     */
    public HsqlDatabase() throws Exception {
        Class.forName(DB_DRIVER);
        connection = DriverManager.getConnection(DB_CONNECTION, "SA", "");

        // create database tables only if they do not yet exist.
        createDatabaseModel(connection);
    }
    
    /**
     * Create the database tables.
     */
    private void createDatabaseModel(Connection conn) {
        try {
            InputStream stream = getClass().getResourceAsStream("/data/DBSetup.script");
            List<String> commands = readAllLines(stream);
            
            for (String line: commands) {
                command(line, conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * use for SQL commands CREATE, DROP, INSERT and UPDATE.
     * 
     * @param expression SQL command
     * @throws SQLException when something went wrong
     */
    private synchronized void command(String expression, Connection connection) throws SQLException {
        Statement st = null;
        st = connection.createStatement(); // statements
        int i = st.executeUpdate(expression); // run the query
        if (i == -1) {
            System.out.println("db error : " + expression);
        }
        st.close();
    }

    /**
     * @return a connection to the database to work with.
     * @throws SQLException whenever something goes wrong.
     */
    public Connection getConnection() throws SQLException {
        return connection;
    }
    
    private List<String> readAllLines(InputStream is) throws IOException {
        List<String> result = new LinkedList<>();
        BufferedReader b = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = b.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

}
