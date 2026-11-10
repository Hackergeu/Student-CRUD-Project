package com.student.demo.service;

import com.student.demo.entity.Student;
import com.student.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student)
    {
        student.setDeleted(false);
        Student res=studentRepository.save(student);
        return res;
    }

    public Student getStudent(Long id)
    {
        Optional<Student> ans=studentRepository.findByIdAndIsDeletedIsFalse(id);
        if(ans.isPresent())
        {
            return ans.get();
        }
        return null;
    }

    public List<Student> getStudents()
    {
        List<Student> students = studentRepository.findByIsDeletedIsFalse();
        return students;
    }

    public Student update(Long id,Student student)
    {
        Optional<Student> ans = studentRepository.findById(id);
        if(ans.isEmpty())
        {
            return null;
        }
        Student newStudent = ans.get();
        newStudent.setAge(student.getAge());
        newStudent.setEmail(student.getEmail());
        newStudent.setName(student.getName());
        newStudent.setRollNo(student.getRollNo());
        newStudent.setFramework(student.getFramework());
        return studentRepository.save(newStudent);
    }

    public boolean deleteById(Long id)
    {
        boolean isStudent=studentRepository.existsById(id);
        if(!isStudent)
        {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    public void deleteInfo()
    {
        studentRepository.deleteAll();
    }

    public boolean delete(Long id)
    {
        Optional<Student> res = studentRepository.findByIdAndIsDeletedIsFalse(id);
        if(res.isEmpty())
        {
            return false;
        }
        Student existing = res.get();
        existing.setDeleted(true);
        studentRepository.save(existing);
        return true;
    }

}
