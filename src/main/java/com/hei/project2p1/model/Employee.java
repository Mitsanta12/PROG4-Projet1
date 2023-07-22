package com.hei.project2p1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    @Lob
    @Column(columnDefinition = "bytea") // Utilisez le type de colonne "bytea"
    private byte[] photo; // Champ pour l'image d'identité (type byte[] pour les données binaires)
}

