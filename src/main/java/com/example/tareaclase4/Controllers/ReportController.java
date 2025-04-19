package com.example.tareaclase4.Controllers;

import com.example.tareaclase4.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/reporte-salario")
    public String reporteSalario(Model model) {
        model.addAttribute("lista", employeeRepository.empleadosConSalarioMayor());
        return "reportes/reporteSalarios";
    }



    @GetMapping("/reporte-departamentos")
    public String reporteDepartamentos(Model model) {
        model.addAttribute("lista", employeeRepository.reporteDepartamentoPorPaisYCiudad());
        return "reportes/reporteDepartamento";
    }

    @GetMapping("/reporte-gerentes")
    public String reporteGerentes(Model model) {
        model.addAttribute("lista", employeeRepository.reporteGerentesConExperiencia());
        return "reportes/reporteGerentes";
    }
}
