package com.example.tareaclase4.Dto;

import java.time.LocalDate;

public interface ReporteSalarioDTO {
    String getNombre();
    String getApellido();
    LocalDate getFechaInicio();
    LocalDate getFechaFin();
    String getPuesto();
}

