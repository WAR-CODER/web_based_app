package wethinkcode.exercises;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import wethinkcode.rdbms.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercise 2
 *
 * Tests for Exercise 2
 */
public class DatabaseTests {
    private static DatabaseManager manager;

    /**
     * Connect to the in-memory SQLite database before each test runs
     * @throws SQLException failed to connect
     */
    @BeforeEach
    public void setup() throws SQLException {
        manager = new DatabaseManager();
    }

    /**
     * Close the connection to the database after eachtests runs
     * @throws SQLException failed to close the connection
     */
    @AfterEach
    public void cleanup() throws SQLException {
        manager.shutdown();
    }

    /**
     * Sample test to ensure that it is possible to run SQL commands
     * @throws IOException The SQL file could not be found
     * @throws SQLException The SQL code failed
     */
    @Test
    public void sampleTable() throws IOException, SQLException {
        assertTrue(manager.executeSql("sample_table.sql"));
        assertTrue(manager.verifyTable(new SampleTableValidator("Sample")));
    }

    /**
     * Test for 2.1
     *
     * @throws IOException the SQL code file was not found
     * @throws SQLException SQL failure
     */
    @Test
//    @Disabled
    public void genresTable() throws IOException, SQLException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.verifyTable(new GenresTableValidator("Genres")));
    }

    /**
     * Test for 2.2
     *
     * @throws IOException the SQL code file was not found
     * @throws SQLException SQL failure
     */
    @Test
//    @Disabled
    public void booksTable() throws IOException, SQLException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.executeSql("create_books.sql"));
        assertTrue(manager.verifyTable(new BooksTableValidator("Books")));
    }

    /**
     * Test for 2.3 (part 1)
     *
     * @throws IOException the SQL code file was not found
     */
    @Test
//    @Disabled
    public void insertGenres() throws IOException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.executeSql("create_books.sql"));
        assertTrue(manager.executeSqlBatch("insert_genres.sql"));
    }

    /**
     * Test for 2.3 (part 2)
     *
     * @throws IOException the SQL code file was not found
     */
    @Test
//    @Disabled
    public void insertBooks() throws IOException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.executeSql("create_books.sql"));
        assertTrue(manager.executeSqlBatch("insert_genres.sql"));
        assertTrue(manager.executeSqlBatch("insert_books.sql"));
    }

    /**
     * Test for 2.4.1
     *
     * @throws IOException the SQL code file was not found
     */
    @Test
//    @Disabled
    public void findAllGenres() throws IOException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.executeSql("create_books.sql"));
        assertTrue(manager.executeSqlBatch("insert_genres.sql"));
        assertTrue(manager.executeSqlBatch("insert_books.sql"));
        assertTrue(manager.verifySelect("find_all_genres.sql", new AllGenresValidator()));
    }

    /**
     * Test for 2.4.2
     *
     * @throws IOException the SQL code file was not found
     */
    @Test
//    @Disabled
    public void findIOGenres() throws IOException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.executeSql("create_books.sql"));
        assertTrue(manager.executeSqlBatch("insert_genres.sql"));
        assertTrue(manager.executeSqlBatch("insert_books.sql"));
        assertTrue(manager.verifySelect("find_io_genres.sql", new IoGenresValidator()));
    }

    /**
     * Test for 2.4.3
     *
     * @throws IOException the SQL code file was not found
     */
    @Test
//    @Disabled
    public void findAllBooks() throws IOException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.executeSql("create_books.sql"));
        assertTrue(manager.executeSqlBatch("insert_genres.sql"));
        assertTrue(manager.executeSqlBatch("insert_books.sql"));
        assertTrue(manager.verifySelect("find_all_books.sql", new AllBooksValidator()));
    }

    /**
     * Test for 2.4.4
     *
     * @throws IOException the SQL code file was not found
     */
    @Test
//    @Disabled
    public void findHistoryBooks() throws IOException {
        assertTrue(manager.executeSql("create_genres.sql"));
        assertTrue(manager.executeSql("create_books.sql"));
        assertTrue(manager.executeSqlBatch("insert_genres.sql"));
        assertTrue(manager.executeSqlBatch("insert_books.sql"));
        assertTrue(manager.verifySelect("find_history_books.sql", new HistoryBooksValidator()));
    }
}
