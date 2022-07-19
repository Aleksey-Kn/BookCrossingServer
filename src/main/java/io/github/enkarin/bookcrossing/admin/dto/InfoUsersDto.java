package io.github.enkarin.bookcrossing.admin.dto;

import io.github.enkarin.bookcrossing.user.dto.UserDto;
import io.github.enkarin.bookcrossing.user.dto.UserParentDto;
import io.github.enkarin.bookcrossing.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Immutable
@EqualsAndHashCode(callSuper = true)
@Getter
@Schema(description = "Данные пользователя для администатора")
public class InfoUsersDto extends UserParentDto {

    @Schema(description = "Время последнего входа", example = "2022-11-03T23:15:09.61")
    private final String loginDate;

    private InfoUsersDto(final int userId, final String name, final String login, final String email, final String city,
                        final boolean accountNonLocked, final boolean enabled, final long loginDate, final int zone) {
        super(userId, name, login, email, city, accountNonLocked, enabled);
        this.loginDate = loginDate == 0 ? "0" :
                LocalDateTime.ofEpochSecond(loginDate, 0, ZoneOffset.ofHours(zone))
                        .toString();
    }

    public static InfoUsersDto fromUser(final User user, final int zone) {
        return new InfoUsersDto(user.getUserId(), user.getName(), user.getLogin(), user.getEmail(),
                user.getCity(), user.isAccountNonLocked(), user.isEnabled(), user.getLoginDate(), zone);
    }

    public static InfoUsersDto fromUserDto(final UserDto user, final int zone) {
        return new InfoUsersDto(user.getUserId(), user.getName(), user.getLogin(), user.getEmail(),
                user.getCity(), user.isAccountNonLocked(), user.isEnabled(), user.getLoginDate(), zone);
    }
}
