package it.city.itcityacademy.service;

import it.city.itcityacademy.entity.Group;
import it.city.itcityacademy.entity.Payment;
import it.city.itcityacademy.entity.Student;
import it.city.itcityacademy.payload.ApiResponse;
import it.city.itcityacademy.payload.ReqPayment;
import it.city.itcityacademy.payload.ResPayment;
import it.city.itcityacademy.repository.GroupRepository;
import it.city.itcityacademy.repository.PaymentRepository;
import it.city.itcityacademy.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    final
    GroupRepository groupRepository;
    final
    StudentRepository studentRepository;
    final
    PaymentRepository paymentRepository;

    public PaymentService(GroupRepository groupRepository, StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;

        this.paymentRepository = paymentRepository;
    }

    public ApiResponse addPayment(ReqPayment reqPayment) {
        Group group = groupRepository.findById(reqPayment.getGroupId()).orElseThrow(() -> new ResourceAccessException("group"));
        Student student = studentRepository.findById(reqPayment.getStudentId()).orElseThrow(() -> new ResourceAccessException("student"));
        Payment payment = new Payment();
        payment.setPayDate(reqPayment.getPayDate());
        payment.setPaySum(reqPayment.getPaySum());
        payment.setStudent(student);
        payment.setGroup(group);
        paymentRepository.save(payment);
        return new ApiResponse("Successfully payment added", true);
    }

    public ApiResponse deletePayment(UUID id) {
        paymentRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }

    public ApiResponse updatePayment(UUID id, ReqPayment reqPayment) {

        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            Optional<Group> group = groupRepository.findById(reqPayment.getGroupId());
            if (group.isPresent()) {
                Optional<Student> student = studentRepository.findById(reqPayment.getStudentId());
                if (student.isPresent()) {
                        Payment payment1 = payment.get();
                        payment1.setStudent(student.get());
                        payment1.setPaySum(reqPayment.getPaySum());
                        payment1.setGroup(group.get());

                        payment1.setPayDate(reqPayment.getPayDate());
                        paymentRepository.save(payment1);
                        return new ApiResponse("Successfully updated", true);
                }
            }
        }
        return new ApiResponse("Error", false);
    }

    public ApiResponse get() {

        List<Payment> paymentList = paymentRepository.findAll();
        List<ResPayment> resPayments = new ArrayList<>();
        for (Payment payment : paymentList) {
            ResPayment resPayment = new ResPayment();
            resPayment.setStudent(payment.getStudent());
            resPayment.setGroup(payment.getGroup());
            resPayment.setPaySum(payment.getPaySum());
            resPayment.setPayDate(payment.getPayDate());
            resPayments.add(resPayment);
        }
        return new ApiResponse(true, resPayments);
    }
}