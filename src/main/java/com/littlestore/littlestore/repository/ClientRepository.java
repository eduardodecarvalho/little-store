package com.littlestore.littlestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlestore.littlestore.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
