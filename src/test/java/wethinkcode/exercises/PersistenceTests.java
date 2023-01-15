package wethinkcode.exercises;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import wethinkcode.persistence.*;
import wethinkcode.rdbms.BooksTableValidator;
import wethinkcode.rdbms.DatabaseManager;
import wethinkcode.rdbms.GenresTableValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercise 3
 * <p>
 * Tests for Exercise 3
 */
public class PersistenceTests {
    private static final Map<String, Genre> ALL_GENRES = Map.of(
            "PROG", new Genre("PROG", "Programming"),
            "BIO", new Genre("BIO", "Biography"),
            "SCIFI", new Genre("SCIFI", "Science Fiction"));

    private static final List<Genre> IO_GENRES = ALL_GENRES.values().stream()
            .filter(genre -> genre.getDescription().contains("io")).toList();

    private static final List<BookGenreView> BOOK_GENRE_VIEW_LIST = List.of(
            new BookGenreView("Test Driven Development", "Programming"),
            new BookGenreView("Programming in Haskell", "Programming"),
            new BookGenreView("Scatterlings of Africa", "Biography"));

    private static DatabaseManager manager;

    /**
     * Connect to the in-memory SQLite database before each test runs
     *
     * @throws SQLException failed to connect
     */
    @BeforeEach
    public void setup() throws SQLException {
        manager = new DatabaseManager();
    }

    /**
     * Close the connection to the database after each tests runs
     *
     * @throws SQLException failed to close the connection
     */
    @AfterEach
    public void cleanup() throws SQLException {
        manager.shutdown();
    }

    /**
     * Test for Exercise 3.1 (part 1)
     */
    @Test
//    @Disabled
    public void badCreate() {
        DummyTables tables = new DummyTables(manager.getConnection());
        assertFalse(tables.badCreate());
    }

    /**
     * Test for Exercise 3.1 (part 2)
     */
    @Test
//    @Disabled
    public void createGenres() throws SQLException {
        Tables tables = new Tables(manager.getConnection());
        assertTrue(tables.createGenres());
        assertTrue(manager.verifyTable(new GenresTableValidator("Genres")));
    }

    /**
     * Test for Exercise 3.1 (part 3)
     */
    @Test
//    @Disabled
    public void createBooks() throws SQLException {
        Tables tables = new Tables(manager.getConnection());
        assertTrue(tables.createBooks());
        assertTrue(manager.verifyTable(new BooksTableValidator("Books")));
    }

    /**
     * Test for Exercise 3.2  (part 1)
     */
    @Test
//    @Disabled
    public void insertGenres() {
        Tables tables = new Tables(manager.getConnection());
        DataLoader loader = new DataLoader(manager.getConnection());
        tables.createGenres();
        assertTrue(loader.insertGenres());
    }

    /**
     * Test for Exercise 3.2  (part 2)
     */
    @Test
//    @Disabled
    public void insertBooks() throws SQLException {
        Tables tables = new Tables(manager.getConnection());
        DataLoader loader = new DataLoader(manager.getConnection());
        tables.createBooks();
        List<Book> books = loader.insertBooks();
        assertTrue(books.stream().allMatch(Book::hasId));
    }

    /**
     * Test for Excerise 3.3 (part 1)
     *
     * @throws SQLException SQL error
     */
    @Test
//    @Disabled
    public void findAllGenres() throws SQLException {
        Finder finder = new Finder(manager.getConnection());
        Tables tables = new Tables(manager.getConnection());
        DataLoader loader = new DataLoader(manager.getConnection());
        tables.createGenres();
        loader.insertGenres();

        List<Genre> results = finder.findAllGenres();
        var expected = ALL_GENRES.values();
        assertEquals(0, expected.stream().dropWhile(results::contains).count());
    }

    /**
     * Test for Excerise 3.3 (part 2)
     *
     * @throws SQLException SQL error
     */
    @Test
//    @Disabled
    public void findGenresLike() throws SQLException {
        Finder finder = new Finder(manager.getConnection());
        Tables tables = new Tables(manager.getConnection());
        DataLoader loader = new DataLoader(manager.getConnection());
        tables.createGenres();
        loader.insertGenres();

        List<Genre> results = finder.findGenresLike("%io%");
        assertEquals(0, IO_GENRES.stream().dropWhile(results::contains).count());

        assertEquals(0, finder.findGenresLike("%xyz%").size());
    }

    /**
     * Test for Excerise 3.3 (part 3)
     *
     * @throws SQLException SQL error
     */
    @Test
//    @Disabled
    public void findBooksAndGenres() throws SQLException {
        Finder finder = new Finder(manager.getConnection());
        Tables tables = new Tables(manager.getConnection());
        DataLoader loader = new DataLoader(manager.getConnection());
        tables.createBooks();
        loader.insertBooks();

        List<BookGenreView> results = finder.findBooksAndGenres();
        assertEquals(0, BOOK_GENRE_VIEW_LIST.stream().dropWhile(results::contains).count());
    }

    /**
     * Test for Excerise 3.3 (part 4)
     *
     * @throws SQLException SQL error
     */
    @Test
//    @Disabled
    public void findNumberOfBooksInGenre() throws SQLException {
        Finder finder = new Finder(manager.getConnection());
        Tables tables = new Tables(manager.getConnection());
        DataLoader loader = new DataLoader(manager.getConnection());
        tables.createBooks();
        loader.insertBooks();

        assertEquals(0, finder.findNumberOfBooksInGenre("HIST"));
        assertEquals(2, finder.findNumberOfBooksInGenre("PROG"));
    }

    /**
     * Class to verify that only Create table commands are allowed.
     */
    static class DummyTables extends Tables {

        public DummyTables(Connection connection) {
            super(connection);
        }

        public boolean badCreate() {
            return createTable("DROP TABLE Genres");
        }
    }
}
