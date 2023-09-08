package com.bozkurt.springboot.thymeleafdemo.controller;

import com.bozkurt.springboot.thymeleafdemo.entity.Employee;
import com.bozkurt.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // add mapping for "/list"

    @GetMapping("/list")
    public String listEmployees(Model theModel) {

        // get employees from db
        List<Employee> employeeList = employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees", employeeList);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set employee in the model to prepopulate the form
        theModel.addAttribute("employee", theEmployee);

        //send over to our form
        return "employees/employee-form";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        // create mode attribute  bind form data
        Employee employee = new Employee();

        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {
        // delete the employee
        employeeService.deleteById(theId);
        // redirect to the /employees/list
        return "redirect:/employees/list";
    }
}









