package wethinkcode.persistence;

/**
 * DO NOT MODIFY THIS CODE
 *
 * A view of the book title and its genre
 */
public class BookGenreView {
    private final String bookTitle;
    private final String genreDescription;

    public BookGenreView(String bookTitle, String genreDescription) {
        this.bookTitle = bookTitle;
        this.genreDescription = genreDescription;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getGenreDescription() {
        return genreDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookGenreView that = (BookGenreView) o;

        if (!bookTitle.equals(that.bookTitle)) return false;
        return genreDescription.equals(that.genreDescription);
    }

    @Override
    public int hashCode() {
        int result = bookTitle.hashCode();
        result = 31 * result + genreDescription.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BookGenreView{" +
                "bookTitle='" + bookTitle + '\'' +
                ", genreDescription='" + genreDescription + '\'' +
                '}';
    }
}
