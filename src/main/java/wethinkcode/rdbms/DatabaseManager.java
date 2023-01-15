package wethinkcode.rdbms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * DO NOT MODIFY THIS CODE
 *
 * A utility class for executing sql statements for exercise 2.
 */
public class DatabaseManager {
    private final Connection connection;

    /**
     * Connect to an in-memory SQLite database
     *
     * @throws SQLException if the connection failed
     */
    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    }

    /**
     * Get the database connection
     * @return the database connection
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Close the database connection
     *
     * @throws SQLException the connection could not be closed
     */
    public void shutdown() throws SQLException {
        connection.close();
    }

    /**
     * Read a SQL file stored in src/main/resources/sql and execute it against the database
     *
     * @param filename the sql file to execute
     * @return true if the statement was successfully executed
     * @throws IOException the sql file was not found
     */
    public boolean executeSql(String filename) throws IOException {
        String sql = readFileInSourceResources(filename);
        try (final PreparedStatement s = connection.prepareStatement(sql)) {
            int result = s.executeUpdate();
            return result == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifySelect(String filename, Predicate<ResultSet> validator) throws IOException {
        String sql = readFileInSourceResources(filename);
        try (final PreparedStatement s = connection.prepareStatement(sql)) {
            if (!s.execute()) return false;
            return validator.test(s.getResultSet());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyTable(TableValidator tableValidator) throws SQLException {
        DatabaseMetaData metaData = this.connection.getMetaData();
        return tableValidator.validate(metaData);
    }

    public boolean executeSqlBatch(String filename) throws IOException {
        String[] inserts = readFileInSourceResources(filename).split(";");
        try (final Statement s = connection.createStatement()) {
            for (String insert : inserts) {
                s.addBatch(insert);
            }
            int[] result = s.executeBatch();
            return Arrays.stream(result).sum() == inserts.length;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String readFileInSourceResources(String filename) throws IOException {
        return readFile(getFile(filename));
    }

    private Path getFile(String filename) {
        return Paths.get("src", "main", "resources", "sql", filename);
    }

    private String readFile(Path path) throws IOException {
        List<String> contents = Files.readAllLines(path);
        return contents.stream().map(String::trim).collect(Collectors.joining(" "));
    }
}
