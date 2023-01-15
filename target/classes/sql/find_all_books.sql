SELECT b.title, g.description
FROM
    Books b , Genres g
    WHERE
    b.genre_code = g.code