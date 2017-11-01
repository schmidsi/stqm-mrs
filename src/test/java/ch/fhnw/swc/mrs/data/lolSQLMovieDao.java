package ch.fhnw.swc.mrs.data;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;

import java.sql.Connection;

public class lolSQLMovieDao extends DBTestCase {

    /** Class Under Test: SQLMovieDao **/
    private SQLMovieDAO dao;
    private IDatabaseTester tester;
    private Connection connection;

    private static final String COUNT_SQL = "SELECT COUNT(*) FROM movies";
    private static final String DB_CONNECTION = "jdbc:hsqldb:mem:mrs";

    /** Create a new Integration Test object. */


    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }
}
