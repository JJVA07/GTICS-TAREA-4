package com.example.tareaclase4.Controllers;

import com.example.tareaclase4.Entity.Employee;
import com.example.tareaclase4.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HistoryController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/historial")
    public String mostrarHistorial(@RequestParam(name = "filtro", required = false) String filtro, Model model) {
        List<Employee> empleados;

        if (filtro != null && !filtro.trim().isEmpty()) {
            empleados = employeeRepository.buscarPorFiltro(filtro);
        } else {
            empleados = employeeRepository.findAll();
        }

        model.addAttribute("empleados", empleados);
        model.addAttribute("filtro", filtro);
        return "history/historial";
    }
}
