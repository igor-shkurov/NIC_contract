package com.example.accountingsystem.entities.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepo extends JpaRepository<Stage, Long> {
}
