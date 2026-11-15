package com.student.demo.service;

import com.student.demo.dto.CreateRequestDTO;
import com.student.demo.dto.CreateResponseDTO;
import com.student.demo.entity.Student;
import com.student.demo.exception.DuplicateResourceException;
import com.student.demo.exception.ResourceNotFoundException;
import com.student.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateResponseDTO createStudent(CreateRequestDTO createRequestDTO)
    {
        Student student = mapToEntity(createRequestDTO);
        if(emailExists(student))
        {
            throw new DuplicateResourceException("This email " + student.getEmail() + "already exists in database");
        }
        Student res=studentRepository.save(student);
        return mapToDto(res);
    }

    public CreateResponseDTO getStudent(Long id)
    {
        Student studentResp = studentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));
        return mapToDto(studentResp);
    }

    public List<CreateResponseDTO> getStudents()
    {
        List<Student> students = studentRepository.findByIsDeletedIsFalse();
        return students.stream().map(this::mapToDto).toList();
    }

    public CreateResponseDTO update(Long id,CreateRequestDTO studentReq)
    {
        Student existingStudent = studentRepository
                .findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        existingStudent.setName(studentReq.getName());
        existingStudent.setRollNo(studentReq.getRollNo());
        existingStudent.setFramework(studentReq.getFramework());
        existingStudent.setAge(studentReq.getAge());
        existingStudent.setDeleted(false);
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(existingStudent);

        return mapToDto(savedStudent);
    }

    public void deleteById(Long id)
    {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        studentRepository.delete(student);
    }

    public void deleteInfo()
    {
        studentRepository.deleteAll();
    }

    public void delete(Long id)
    {
        Student existing = studentRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        existing.setDeleted(true);
        studentRepository.save(existing);
    }

    private Student mapToEntity(CreateRequestDTO student)
    {
        Student stud = new Student();
        stud.setName(student.getName());
        stud.setAge(student.getAge());
        stud.setRollNo(student.getRollNo());
        stud.setEmail(student.getEmail());
        stud.setFramework(student.getFramework());
        stud.setCreatedAt(LocalDateTime.now());
        stud.setUpdatedAt(LocalDateTime.now());

        stud.setDeleted(false);

        return stud;
    }

    private CreateResponseDTO mapToDto(Student stud)
    {
        CreateResponseDTO create = new CreateResponseDTO();
        create.setId(stud.getId());
        create.setName(stud.getName());
        create.setAge(stud.getAge());
        create.setRollNo(stud.getRollNo());
        create.setEmail(stud.getEmail());
        create.setFramework(stud.getFramework());
        create.setUpdatedAt(stud.getCreatedAt());

        return create;
    }

    private boolean emailExists(Student student)
    {
        return studentRepository.existsByEmail(student.getEmail());
    }

}
