package ru.list.surkovr.gymservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.list.surkovr.gymservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
