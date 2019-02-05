package com.springboot.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.cursomc.domain.Categoria;

@Repository
public interface CatergoriaRepository extends JpaRepository<Categoria, Integer> {

}
