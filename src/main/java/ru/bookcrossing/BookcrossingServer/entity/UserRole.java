package ru.bookcrossing.BookcrossingServer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "t_role_id")
    private Role t_role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "t_user_id")
    private User t_user;

    public UserRole(Role r, User u){
        t_role = r;
        t_user = u;
    }
}
