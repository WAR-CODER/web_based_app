package wethinkcode.persistence;

/**
 * DO NOT MODIFY THIS CODE
 *
 * The genre for a book
 */
public class Genre {
    private final String code;
    private final String description;

    /**
     * Create a new Genre object
     *
     * @param code        The unique short code for a genre
     * @param description The full description of the genre
     */
    public Genre(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Get the short code of the genre
     *
     * @return the genre's short code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the description of the genre
     *
     * @return the desciption of the genre
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        return code.equals(genre.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "Genre{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
