package com.example.tareaclase4.Repository;

import com.example.tareaclase4.Dto.ReporteDepartamentosDTO;
import com.example.tareaclase4.Dto.ReporteGerentesDTO;
import com.example.tareaclase4.Dto.ReporteSalarioDTO;
import com.example.tareaclase4.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN e.job j " +
            "LEFT JOIN e.department d " +
            "LEFT JOIN d.location l " +
            "LEFT JOIN l.country c " +
            "WHERE LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(l.city) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(c.countryName) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Employee> buscarPorFiltro(@Param("filtro") String filtro);

    // ✅ Reporte de empleados con salario mayor a 15000
    @Query("SELECT e.firstName AS nombre, e.lastName AS apellido, jh.startDate AS fechaInicio, jh.endDate AS fechaFin, j.jobTitle AS puesto " +
            "FROM JobHistory jh JOIN jh.employee e JOIN jh.job j WHERE e.salary > 15000")
    List<ReporteSalarioDTO> empleadosConSalarioMayor();





    // ✅ Reporte de departamentos con más de 3 empleados
    @Query("SELECT c.countryName AS pais, l.city AS ciudad, COUNT(d) AS cantidad " +
            "FROM Department d " +
            "JOIN d.location l " +
            "JOIN l.country c " +
            "JOIN d.employees e " +
            "GROUP BY c.countryName, l.city " +
            "HAVING COUNT(e) > 3")
    List<ReporteDepartamentosDTO> reporteDepartamentoPorPaisYCiudad();

    // ✅ Reporte de gerentes con más de 5 años de experiencia (usamos YEAR DIFF)
    @Query("SELECT d.departmentName AS departmentName, e.firstName AS firstName, e.lastName AS lastName, e.salary AS salary " +
            "FROM Department d JOIN d.manager e " +
            "WHERE FUNCTION('datediff', CURRENT_DATE, e.hireDate) > 365 * 5")
    List<ReporteGerentesDTO> reporteGerentesConExperiencia();





}
