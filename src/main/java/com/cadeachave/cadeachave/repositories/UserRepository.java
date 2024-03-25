package com.cadeachave.cadeachave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.cadeachave.cadeachave.models.ProfessorModel;
import com.cadeachave.cadeachave.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
    UserDetails findByLogin(String login);
    UserModel findByProfessor(ProfessorModel professor);
}
