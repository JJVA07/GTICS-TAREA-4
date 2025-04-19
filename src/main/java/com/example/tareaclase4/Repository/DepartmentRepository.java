package com.example.tareaclase4.Repository;

import com.example.tareaclase4.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
