package io.github.enkarin.bookcrossing.user.dto;

import io.github.enkarin.bookcrossing.books.dto.BookModelDto;
import io.github.enkarin.bookcrossing.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "Данные пользователя для внутреннего пользования")
public class UserProfileDto {

    @Schema(description = "Идентификатор", example = "0")
    private final int userId;

    @Schema(description = "Имя", example = "Alex")
    private final String name;

    @Schema(description = "Логин", example = "Alex")
    private final String login;

    @Schema(description = "Город", example = "Новосибирск")
    private final String city;

    @Schema(description = "Почтовый адрес", example = "kar@mail.ru")
    private final String email;

    @Schema(description = "Книги пользователя")
    private final Set<BookModelDto> books;

    public static UserProfileDto fromUser(final User user) {
        final Set<BookModelDto> books = user.getBooks().stream()
                .map(BookModelDto::fromBook)
                .collect(Collectors.toUnmodifiableSet());
        return new UserProfileDto(user.getUserId(), user.getName(), user.getLogin(), user.getCity(),
                user.getEmail(), books);
    }
}
