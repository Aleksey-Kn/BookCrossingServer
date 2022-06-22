package io.github.enkarin.bookcrossing.user.dto;

import io.github.enkarin.bookcrossing.books.model.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Данные пользователя для внутреннего пользования")
public class UserProfileResponse {

    @Schema(description = "Идентификатор", example = "0")
    private int userId;

    @Schema(description = "Имя", example = "Alex")
    private String name;

    @Schema(description = "Логин", example = "Alex")
    private String login;

    @Schema(description = "Город", example = "Новосибирск")
    private String city;

    @Schema(description = "Почтовый адрес", example = "kar@mail.ru")
    private String email;

    @Schema(description = "Книги пользователя")
    private Set<Book> books;
}