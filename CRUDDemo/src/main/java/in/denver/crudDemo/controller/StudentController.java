package in.denver.crudDemo.controller;

import in.denver.crudDemo.entity.Student;
import in.denver.crudDemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createdStudent=studentService.createStudent(student);

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(createdStudent);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Student studentResponse=studentService.getStudent(id);

        if(studentResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(studentResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getStudent(){
        List<Student> studentListResponse=studentService.getAllStudent();
        if(studentListResponse == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(studentListResponse);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody Student studentRequest){
        Student studentResponse=studentService.updateStudent(id,studentRequest);

        if(studentResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(studentResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        Boolean isDeleted= studentService.deleteStudent(id);
        if(!isDeleted)  return ResponseEntity.notFound().build();
        return ResponseEntity.ok("Student deleted");
    }

    @PatchMapping("softDelete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id,
                                             @RequestBody Student studentRequest){
        Boolean isDeleted = studentService.deleteStudentSoftly(id);

        if(!isDeleted)  return  ResponseEntity.notFound().build();
        return ResponseEntity.ok("Record soft deleted");
    }
}
