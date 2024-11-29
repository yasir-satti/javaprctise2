package dev.yasir.javapractise2.service;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentsService {

    private final StudentsRepository studentsRepository;

    public StudentsService(StudentsRepository studentsRepository){
        this.studentsRepository = studentsRepository;
    }

    public List<Student> retrieveAll() throws Exception {

        List<Student> studentsList = studentsRepository.findAll();
        if (studentsList.isEmpty()) {
            throw new Exception("No Student records were found.");
        }
        return studentsList;
    }

    public void deleteAll() throws Exception {
        List<Student> studentsList = studentsRepository.findAll();
        if (studentsList.isEmpty()) {
            throw new Exception("No Student records were found.");
        } else {
            studentsRepository.deleteAll();
        }
    }

    public Optional<Student> retrieveById(int Id) throws Exception {
        Optional<Student> record = studentsRepository.findById(Id);
        if (record.isPresent()) {
            return Optional.of(record.get());
        } else {
            throw new Exception("No Student record was found with Id " +
                    Integer.toHexString(Id) + ".");
        }
    }

    public Long insertRecord(Student newRecord) throws Exception {
        boolean recordExists = studentsRepository.existsById(newRecord.getId().intValue());
        if (recordExists) {
            throw new Exception("Record with Id " +
                    Integer.toHexString(newRecord.getId().intValue()) +
                    " already exists in database.");
        } else {
            Student savedRecord = studentsRepository.save(newRecord);
            return savedRecord.getId();
        }
    }

    public Student update(Student expectedUpdatedRecord, Long id) throws Exception {
        boolean recordExists = studentsRepository.existsById(id.intValue());
        if (recordExists) {
            return studentsRepository.save(expectedUpdatedRecord);
        } else {
            throw new Exception("No Student record with Id " +
                    Long.toHexString(expectedUpdatedRecord.getId()) +
                    " was found in database to update.");
        }
    }
}
