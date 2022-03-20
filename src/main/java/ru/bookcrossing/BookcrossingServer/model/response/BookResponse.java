package ru.bookcrossing.BookcrossingServer.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.bookcrossing.BookcrossingServer.entity.Book;

@Schema(description = "Данные книги для общего доступа")
@Data
public class BookResponse {
    @Schema(description = "Название", example = "Портрет Дориана Грея")
    private String title;

    @Schema(description = "Автор", example = "Оскар Уайльд")
    private String author;

    @Schema(description = "Жанр", example = "Классическая проза")
    private String genre;

    @Schema(description = "Издательство", example = "АСТ")
    private String publishingHouse;

    @Schema(description = "Год издания", example = "2004")
    private int year;

    public BookResponse(Book book){
        title = book.getTitle();
        author = book.getAuthor();
        genre = book.getGenre();
        publishingHouse = book.getPublishingHouse();
        year = book.getYear();
    }
}