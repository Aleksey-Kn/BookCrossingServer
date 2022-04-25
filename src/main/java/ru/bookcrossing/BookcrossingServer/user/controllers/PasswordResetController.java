package ru.bookcrossing.BookcrossingServer.user.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bookcrossing.BookcrossingServer.approvemail.service.MailService;
import ru.bookcrossing.BookcrossingServer.errors.ErrorListResponse;
import ru.bookcrossing.BookcrossingServer.user.dto.UserPasswordDto;
import ru.bookcrossing.BookcrossingServer.user.service.ResetPasswordService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Tag(
        name = "Сброс пароля",
        description = "Позволяет получить на привязанную почту ссылку для сброса пароля"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/reset")
public class PasswordResetController {

    private final MailService mailService;
    private final ResetPasswordService resetPasswordService;

    @Operation(
            summary = "Запрос на сброс пароля",
            description = "Отправляет на почту ссылку для сброса пароля"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Пользователь с таким email не найден",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorListResponse.class))}),
            @ApiResponse(responseCode = "200", description = "Отправляет ссылку на сброс пароля на почту")
    }
    )
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam String email){
        ErrorListResponse response = new ErrorListResponse();
        boolean result = mailService.sendResetPassword(email);
        if(result)
            return new ResponseEntity<>(HttpStatus.OK);
        else {
            response.getErrors().add("user: Пользователь с таким email не найден");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Смена пароля",
            description = "Смена пароля по ссылке"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Введенный пароль некорректен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorListResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Ссылка недействительна",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorListResponse.class))}),
            @ApiResponse(responseCode = "200", description = "Пароль успешно изменен")
    }
    )
    @PostMapping("/update")
    public ResponseEntity<?> updatePassword(@RequestParam String token,
                                            @Valid @RequestBody UserPasswordDto userPasswordDto,
                                            BindingResult bindingResult){
        ErrorListResponse finalResponse = new ErrorListResponse();
        if(!userPasswordDto.getPassword().equals(userPasswordDto.getPasswordConfirm())){
            finalResponse.getErrors().add("password: Пароли не совпадают");
            return new ResponseEntity<>(finalResponse, HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(f -> finalResponse.getErrors()
                    .add(Objects.requireNonNull(f.getDefaultMessage())));
            return new ResponseEntity<>(finalResponse, HttpStatus.BAD_REQUEST);
        }
        Optional<ErrorListResponse> response = resetPasswordService.updatePassword(token, userPasswordDto);
        if(response.isEmpty())
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(response.get(), HttpStatus.FORBIDDEN);
    }
}