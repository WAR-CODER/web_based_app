package wethinkcode.persistence;

import java.util.Objects;

/**
 * DO NOT MODIFY THIS CODE
 *
 * A Book
 */
public class Book {
    private static final Integer NO_ID = -1;
    private final String title;
    private final Genre genre;
    private Integer id;

    /**
     * Create a new book.
     * Note that a newly created book does not have a valid id.
     * The should be assigned by the data access object that saves the book to the database.
     *
     * @param title The title of the book
     * @param genre The genre of the book
     */
    public Book(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
        this.id = NO_ID;
    }

    /**
     * Assign an id to the book.
     * This is usually an id value obtained from the database.
     *
     * @param id The id for the book
     */
    public void assignId(int id) {
        this.id = id;
    }

    /**
     * Check if the book has an assigned id
     *
     * @return true if the book has an assigned id
     */
    public boolean hasId() {
        return !Objects.equals(id, NO_ID);
    }

    /**
     * Get the id.
     * If the id has not yet been assigned, it throws an unchecked exception.
     *
     * @return the id
     */
    public Integer getId() {
        if (!hasId()) throw new RuntimeException("No id for Book");
        return id;
    }

    /**
     * The title of the book
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The genre of the book
     *
     * @return The genre
     */
    public Genre getGenre() {
        return genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!Objects.equals(id, book.id)) return false;
        if (!title.equals(book.title)) return false;
        return genre.equals(book.genre);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + genre.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                '}';
    }
}
