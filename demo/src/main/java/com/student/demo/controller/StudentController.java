package com.student.demo.controller;

import com.student.demo.dto.CreateRequestDTO;
import com.student.demo.dto.CreateResponseDTO;
import com.student.demo.entity.Student;
import com.student.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService)
    {
        this.studentService=studentService;
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createStudent(@Valid @RequestBody CreateRequestDTO createRequestDTO)
    {
        CreateResponseDTO res=studentService.createStudent(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateResponseDTO> getStudentById(@PathVariable Long id)
    {
        CreateResponseDTO res=studentService.getStudent(id);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping
    public ResponseEntity<List<CreateResponseDTO>> getAllStudents()
    {
        List<CreateResponseDTO> students = studentService.getStudents();
        return ResponseEntity.ok().body(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateResponseDTO> updateStudent(@PathVariable Long id, @RequestBody CreateRequestDTO createRequestDTO)
    {
        CreateResponseDTO updated = studentService.update(id,createRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        studentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRecords()
    {
        studentService.deleteInfo();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id)
    {
         studentService.delete(id);
         return ResponseEntity.noContent().build();
    }

}
