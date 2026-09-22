package in.denver.crudDemo.service;

import in.denver.crudDemo.entity.Student;
import in.denver.crudDemo.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }


    public Student createStudent(Student studentRequest){
        studentRequest.setDeleted(false);
        Student studentResponse=studentRepository.save(studentRequest);

        return studentResponse;
    }


    public Student getStudent(Long id){
        Optional<Student> student=studentRepository.findByIdAndDeletedIsFalse(id);
        if(student.isPresent()) return student.get();

        return null;
    }


    public List<Student> getAllStudent(){
        List<Student> studentList=studentRepository.findByDeletedIsFalse();

        return studentList;
    }


    public Student updateStudent(Long id, Student studentReq){
        Optional<Student> existingStudent=studentRepository.findById(id);

        if(existingStudent.isEmpty())   return null;

        Student studentToUpdate = existingStudent.get();
        studentToUpdate.setName(studentReq.getName());
        studentToUpdate.setAge(studentReq.getAge());
        studentToUpdate.setRollNo(studentReq.getRollNo());
        studentToUpdate.setEmail(studentReq.getEmail());
        studentToUpdate.setSubject(studentReq.getSubject());
        studentToUpdate.setDeleted(false);

        return studentRepository.save(studentToUpdate);

    }


    public Boolean deleteStudent(Long id){
        Boolean studentExists=studentRepository.existsById(id);

        if(!studentExists) return false;

        studentRepository.deleteById(id);
        return true;
    }


    public  Boolean deleteStudentSoftly(Long id){
        Optional<Student> existingStudent=studentRepository.findByIdAndDeletedIsFalse(id);

        if(!existingStudent.isEmpty()) return false;

        Student studentToMarkDeleted=existingStudent.get();
        studentToMarkDeleted.setDeleted(true);
        studentRepository.save(studentToMarkDeleted);

        return true;
    }
}
