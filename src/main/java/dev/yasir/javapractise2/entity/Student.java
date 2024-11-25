package dev.yasir.javapractise2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Students")
public class Student {
    @Id
    @Generated
    private Long id;

    private String name;
    private String lastname;
    private int studentid;

    public Student(String name, String lastname, int studentid) {
        this.name = name;
        this.lastname = lastname;
        this.studentid = studentid;
    }

    /*    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }*/
}
