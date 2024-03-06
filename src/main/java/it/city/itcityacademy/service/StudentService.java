package it.city.itcityacademy.service;

import it.city.itcityacademy.entity.Group;
import it.city.itcityacademy.entity.Student;
import it.city.itcityacademy.entity.enums.CameFromEnum;
import it.city.itcityacademy.entity.enums.UserStatus;
import it.city.itcityacademy.payload.ApiResponse;
import it.city.itcityacademy.payload.ReqStudent;
import it.city.itcityacademy.payload.ResPageable;
import it.city.itcityacademy.payload.ResStudent;
import it.city.itcityacademy.repository.GroupRepository;
import it.city.itcityacademy.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    final StudentRepository studentRepository;
    final GroupRepository groupRepository;

    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    public ApiResponse addStudent(ReqStudent reqStudent) {
        saveStudent(reqStudent, new Student());
        return new ApiResponse("Student saved", true);
    }


    public ApiResponse getStudents() {
        List<Student> students = studentRepository.findAll();
        List<ResStudent> resStudents = new ArrayList<>();
        for (Student student : students) {
            ResStudent resStudent = new ResStudent();
            resStudent.setFirstName(student.getFirstName());
            resStudent.setLastName(student.getLastName());
            resStudent.setPhoneNumber(student.getPhoneNumber());
            resStudent.setAge(student.getAge());
            resStudent.setRegistrationDay(student.getRegistrationDay());
            resStudent.setId(student.getId());
            resStudent.setDiscount(student.getDiscount());
            resStudent.setUserStatus(String.valueOf(student.getUserStatus()));
            resStudent.setCameFrom(String.valueOf(student.getCameFromEnum()));
            resStudent.setGroupId(student.getGroup().getId());
            resStudent.setGroupName(student.getGroup().getName());
            resStudents.add(resStudent);
        }
        return new ApiResponse(true, resStudents);
    }

    public ApiResponse deleteStudent(UUID id) {
        studentRepository.deleteById(id);
        return new ApiResponse("Student successfully deleted", true);
    }

    public ApiResponse updateStudent(UUID id, ReqStudent reqStudent) {
        saveStudent(reqStudent, studentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("User Not Found")));
        return new ApiResponse("User successfully updated", true);
    }

    private void saveStudent(ReqStudent reqStudent, Student student) {
        Optional<Group> byId = groupRepository.findById(reqStudent.getGroupId());
        if (byId.isPresent()) {
            student.setGroup(byId.get());
            student.setUserStatus(UserStatus.valueOf(reqStudent.getUserStatus()));
        } else {
            student.setGroup(null);
            student.setUserStatus(UserStatus.NEW);
        }
        student.setFirstName(reqStudent.getFirstName());
        student.setLastName(reqStudent.getLastName());
        student.setPhoneNumber(reqStudent.getPhoneNumber());
        student.setAge(reqStudent.getAge());
        student.setRegistrationDay(reqStudent.getRegistrationDay());
        student.setDiscount(reqStudent.getDiscount());
        student.setCameFromEnum(CameFromEnum.valueOf(reqStudent.getCameFrom()));
        studentRepository.save(student);
    }

    public ResStudent getStudent(UUID id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("GetStudent"));
        ResStudent resStudent = new ResStudent();
        resStudent.setFirstName(student.getFirstName());
        resStudent.setLastName(student.getLastName());
        resStudent.setPhoneNumber(student.getPhoneNumber());
        resStudent.setAge(student.getAge());
        resStudent.setRegistrationDay(student.getRegistrationDay());
        resStudent.setId(student.getId());
        resStudent.setDiscount(student.getDiscount());
        resStudent.setUserStatus(String.valueOf(student.getUserStatus()));
        resStudent.setCameFrom(String.valueOf(student.getCameFromEnum()));
        resStudent.setGroupId(student.getGroup().getId());
        resStudent.setGroupName(student.getGroup().getName());
        return resStudent;
    }

    public ApiResponse getStudentPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return new ApiResponse(true, new ResPageable(page, size, studentPage.getTotalPages(), studentPage.getTotalElements(), studentPage.getContent().stream().map(student -> getStudent(student.getId())).collect(Collectors.toList())));
    }

    public ApiResponse getUserStatus() {
        UserStatus[] values = UserStatus.values();
        return new ApiResponse(true, values);
    }

    public ApiResponse getCameFrom() {
        CameFromEnum[] values = CameFromEnum.values();
        return new ApiResponse(true, values);
    }
}
