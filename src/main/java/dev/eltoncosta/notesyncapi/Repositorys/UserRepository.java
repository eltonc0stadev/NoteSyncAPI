package dev.eltoncosta.notesyncapi.Repositorys;

import dev.eltoncosta.notesyncapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //metodo para achar um usuario pelo email
    User findByEmail(String email);
}
