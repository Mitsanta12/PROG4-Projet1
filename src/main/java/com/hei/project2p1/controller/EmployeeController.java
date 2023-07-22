package com.hei.project2p1.controller;

import com.hei.project2p1.model.Employee;
import com.hei.project2p1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/")
    public String index(Model model) {
        // Récupère la liste des employés à partir du service
        // et l'ajoute au modèle pour l'affichage dans la vue
        model.addAttribute("employees", employeeService.getEmployees());

        // Ajoute un nouvel employé vide au modèle pour le formulaire
        model.addAttribute("newEmployee", new Employee());
        return "index";
    }

    @GetMapping("/UploadImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        // Récupérer l'employé par son id en utilisant le service
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);

        // Vérifier si l'employé existe dans la base de données
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            // Récupérer les données binaires de la propriété 'photo' de l'employé
            byte[] photoData = employee.getPhoto();

            // Vérifier si l'employé a une photo
            if (photoData != null && photoData.length > 0) {
                // Construire une réponse HTTP avec les données binaires en tant que corps de la réponse
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.setContentLength(photoData.length);
                return new ResponseEntity<>(photoData, headers, HttpStatus.OK);
            }
        }

        // Renvoyer une réponse vide si l'employé n'existe pas dans la base de données ou s'il n'a pas de photo
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

@PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("newEmployee") Employee employee, @RequestParam("photo")
    MultipartFile photo ){try{
        byte[] photoByte= photo.getBytes();
        employee.setPhoto(photoByte);

    }catch ( Exception e){
        e.printStackTrace();
    }

        employeeService.save(employee);
        return "redirect:/";
    }

}
