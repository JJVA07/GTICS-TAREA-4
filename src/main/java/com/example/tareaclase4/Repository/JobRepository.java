package com.example.tareaclase4.Repository;

import com.example.tareaclase4.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String> {
}