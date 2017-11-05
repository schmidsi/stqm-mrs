package ch.fhnw.swc.mrs.datagenerator;

import java.sql.Connection;

public interface Dataloader {
    /**
     * Load data into MRS database.
     * @param connection to use when writing to database.
     * @throws Exception whenever something goes wrong.
     */
    void load(Connection connection) throws Exception;
}
