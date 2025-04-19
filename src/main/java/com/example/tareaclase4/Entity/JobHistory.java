package com.example.tareaclase4.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "job_history")
@Getter
@Setter
public class JobHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Clave primaria artificial (puedes adaptarla si la base tiene composite key)

    @ManyToOne
    @JoinColumn(name = "employee_id") // Relación con Employee
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "job_id") // Relación con Job
    private Job job;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
