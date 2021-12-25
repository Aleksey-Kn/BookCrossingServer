package ru.bookcrossing.BookcrossingServer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bookcrossing.BookcrossingServer.model.DTO.BookDTO;
import ru.bookcrossing.BookcrossingServer.service.BookService;

import javax.validation.Valid;
import java.util.Objects;

@Tag(
        name="Раздел работы с книгами",
        description="Позволяет ользователю управлять своими книгами"
)
@RestController
@RequestMapping("/user/myBook")
public class BookController {

    BookService bookService;

    @Autowired
    private void setBookService(BookService s){
        bookService = s;
    }

    @Operation(
            summary = "Добавление книги",
            description = "Позволяет сохранить книгу для обмена"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Введены некорректные данные")}
    )
    @PostMapping("/save")
    public Object saveBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder allErrorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(f -> allErrorMessage.append(
                    Objects.requireNonNull(f.getDefaultMessage())).append("\n"));
            return new ResponseEntity<>(allErrorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        bookService.saveBook(bookDTO);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Operation(
            summary = "Список книг",
            description = "Позволяет получить список всех книг пользователя"
    )
    @GetMapping("/getAll")
    public Object bookList(){
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

}