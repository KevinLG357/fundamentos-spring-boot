package com.fundamentosplatzi.springboot.fundamentos.repository;

import com.fundamentosplatzi.springboot.fundamentos.dto.userDto;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByUserEmail(String email);
    @Query("SELECT u FROM User u WHERE u.name LIKE ?1")
    List<User> findAndSort(String name, Sort sort);

    List<User> findByName(String name);
    Optional<User> findByEmailAndName(String email, String name);

    List<User> findByNameLike(String name);

    List<User> findByNameOrEmail(String name, String email);

    List<User> findByBirthDateBetween(LocalDate begin, LocalDate end);

    @Query("SELECT new com.fundamentosplatzi.springboot.fundamentos.dto.userDto(u.id, u.name, u.birthDate) FROM User u WHERE u.birthDate =:parametroFecha AND u.name =:parametroNombre")
    Optional<userDto> getAllByBirthDateAndName(@Param("parametroFecha") LocalDate date, @Param("parametroNombre") String name);
}
