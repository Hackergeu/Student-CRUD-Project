package com.student.demo.controller;

import com.student.demo.entity.Student;
import com.student.demo.service.StudentService;
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
    public ResponseEntity<Student> createStudent(@RequestBody Student student)
    {
        Student res=studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id)
    {
        Student res=studentService.getStudent(id);
        if(res == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(res);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents()
    {
        List<Student> students = studentService.getStudents();
        if(students.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable Long id, @RequestBody Student student)
    {
        Student updated = studentService.update(id,student);
        if(updated == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id)
    {
        boolean exists = studentService.deleteById(id);
        if(!exists)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record Deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRecords()
    {
        studentService.deleteInfo();
        return ResponseEntity.ok("All Records Deleted Successfully");
    }

}
