package africa.bookvault.service;

import africa.bookvault.models.Book;

import java.util.Optional;
import java.util.UUID;

public interface IBookService {

    Book addBook(Book book);

    Optional<Book> findById(UUID bookId);
}
