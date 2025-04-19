package com.example.tareaclase4.Controllers;

import com.example.tareaclase4.Entity.Employee;
import com.example.tareaclase4.Repository.EmployeeRepository;
import com.example.tareaclase4.Repository.JobRepository;
import com.example.tareaclase4.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // ✅ Mostrar lista de empleados
    @GetMapping
    public String listarEmpleados(Model model) {
        List<Employee> empleados = employeeRepository.findAll();
        model.addAttribute("empleados", empleados);
        return "employee/lista";
    }

    // ✅ Formulario nuevo empleado
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("listaPuestos", jobRepository.findAll());
        model.addAttribute("listaDepartamentos", departmentRepository.findAll());
        model.addAttribute("listaEmpleados", employeeRepository.findAll()); // para elegir jefe
        return "employee/crear";
    }

    // ✅ Guardar nuevo empleado
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("employee") Employee empleado, RedirectAttributes attr) {
        if (empleado.getEmail() != null && empleado.getEmail().contains("@")) {
            String sinDominio = empleado.getEmail().split("@")[0];
            empleado.setEmail(sinDominio.toUpperCase());
        }
        if (empleado.getHireDate() == null) {
            empleado.setHireDate(LocalDate.now());
        }

        employeeRepository.save(empleado);
        attr.addFlashAttribute("msg", "Empleado guardado exitosamente");
        return "redirect:/empleados";
    }

    // ✅ Formulario editar
    @GetMapping("/editar/{id}")
    public String editarEmpleadoForm(@PathVariable("id") Integer id, Model model) {
        Employee empleado = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        model.addAttribute("empleado", empleado);
        model.addAttribute("trabajos", jobRepository.findAll());
        model.addAttribute("departamentos", departmentRepository.findAll());
        model.addAttribute("listaJefes", employeeRepository.findAll());
        return "employee/editar";
    }

    // ✅ Actualizar empleado
    @PostMapping("/actualizar")
    public String actualizarEmpleado(@ModelAttribute("empleado") Employee empleado) {
        if (empleado.getEmail() != null && empleado.getEmail().contains("@")) {
            String sinDominio = empleado.getEmail().split("@")[0];
            empleado.setEmail(sinDominio.toUpperCase());
        }

        employeeRepository.save(empleado);
        return "redirect:/empleados";
    }

    // ✅ Eliminar empleado
    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") Integer id, RedirectAttributes attr) {
        employeeRepository.deleteById(id);
        attr.addFlashAttribute("msg", "Empleado eliminado exitosamente");
        return "redirect:/empleados";
    }

    // ✅ Buscar empleados por filtro
    @GetMapping("/buscar")
    public String buscar(@RequestParam("filtro") String filtro, Model model) {
        List<Employee> empleados;
        if (filtro == null || filtro.trim().isEmpty()) {
            empleados = employeeRepository.findAll();
        } else {
            empleados = employeeRepository.buscarPorFiltro(filtro);
        }
        model.addAttribute("empleados", empleados);
        model.addAttribute("filtro", filtro); // para mantener el texto en el input
        return "employee/lista";
    }

}
