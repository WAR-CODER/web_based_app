package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3.3
 */
public class Finder {

    private final Connection connection;

    /**
     * Create an instance of the Finder object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public Finder(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.3 (part 1) Complete this method
     * <p>
     * Finds all genres in the database
     *
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findAllGenres() throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        String query = "SELECT * FROM Genres";
        try ( final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            final boolean gotResultSet = preparedStatement.execute();
            if (!gotResultSet) {
                throw new RuntimeException("Could not findAllGenres");
            }

            try (ResultSet resultSet = preparedStatement.getResultSet()) {

                while (resultSet.next()) {

                    final String gener_code = resultSet.getString("code");
                    final String description = resultSet.getString("description");
                    genreList.add(new Genre(gener_code, description));


                }


            }

        }

        return genreList;

    }

    /**
     * 3.3 (part 2) Complete this method
     * <p>
     * Finds all genres in the database that have specific substring in the description
     *
     * @param pattern The pattern to match
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findGenresLike(String pattern) throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        String query = "select * from Genres  WHERE description LIKE '"+pattern+"'";


        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final boolean gotResultS = preparedStatement.execute();
            final boolean gotResultSet = preparedStatement.execute();
            if (!gotResultSet) {
                throw new RuntimeException("Could not findAllGenres");
            }
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    final String gener_code = resultSet.getString("code");
                    final String description = resultSet.getString("description");
                    genreList.add(new Genre(gener_code, description));
                }
            }
        }


        return genreList;
    }



    /**
     * 3.3 (part 3) Complete this method
     * <p>
     * Finds all books with their genres
     *
     * @return a list of `BookGenreView` objects
     * @throws SQLException the query failed
     */
    public List<BookGenreView> findBooksAndGenres() throws SQLException {


        List<BookGenreView> BookList= new ArrayList<>();
        String query ="SELECT b.title, g.description" +
                "    FROM" +
                "    Books b, Genres g" +
                "    WHERE" +
                "    b.genre_code =g.code";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
            boolean gotResultSet = preparedStatement.execute();

            if (!gotResultSet) {
                throw new RuntimeException("Could not execute query");

            }


            try (ResultSet resultSet = preparedStatement.getResultSet()) {
//
                while (resultSet.next()) {
                    String code = resultSet.getString("title");
                    String message = resultSet.getString("description");
                    BookGenreView view = new BookGenreView(code, message);
                    BookList.add(view);



                }
            }

            return BookList;
        }

    }

    /**
     * 3.3 (part 4) Complete this method
     * <p>
     * Finds the number of books in a genre
     *
     * @return the number of books in the genre
     * @throws SQLException the query failed
     */
    public int findNumberOfBooksInGenre(String genreCode) throws SQLException {
        String query = "SELECT COUNT(*)" +
                "FROM Books " +
                "WHERE genre_code =?";


        List<BookGenreView> genreList = new ArrayList<>();
        int number = 0;
        final PreparedStatement preparedStatement;


        try  {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, genreCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getInt(1);



            }
        }catch (SQLException e){
            e.printStackTrace();
        }





        return number;

    }
}
