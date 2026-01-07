package com.kalanblow.school_management.service;

import com.kalanblow.school_management.application.dashboard.StudentCreatedEvent;
import com.kalanblow.school_management.application.dashboard.StudentDeletedEvent;
import com.kalanblow.school_management.application.dashboard.StudentUpdatedEvent;
import com.kalanblow.school_management.infrastructure.web.dto.StudentCreateDTO;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.model.parent.Parent;
import com.kalanblow.school_management.model.shared.Email;
import com.kalanblow.school_management.model.shared.User;
import com.kalanblow.school_management.model.shared.UserName;
import com.kalanblow.school_management.model.shared.util.CalculateUserAge;
import com.kalanblow.school_management.model.shared.util.KaladewnUtility;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.repository.*;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final EtablissementRepository etablissementRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ParentRepository parentRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final ApplicationEventPublisher eventPublisher;

    public StudentService(StudentRepository studentRepository, EtablissementRepository etablissementRepository, SchoolClassRepository schoolClassRepository, ParentRepository parentRepository, AnneeScolaireRepository anneeScolaireRepository, ApplicationEventPublisher eventPublisher) {
        this.studentRepository = studentRepository;

        this.etablissementRepository = etablissementRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.parentRepository = parentRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     *
     * @param studentCreateDTO to be created
     * @return a new student
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public Student createNewStudent(StudentCreateDTO studentCreateDTO) {

        Etablissement etablissement = etablissementRepository.findById(studentCreateDTO.getEtablissementId()).orElseThrow(() -> new EntityNotFoundException("L'établissement n'a pas été trouvé avec cet id " + studentCreateDTO.getEtablissementId()));
        SchoolClass schoolClass = null;
        if (studentCreateDTO.getSchoolClassId() != null) {
            schoolClass = schoolClassRepository.findSchoolClassById(studentCreateDTO.getSchoolClassId()).orElseThrow(() -> new EntityNotFoundException("la classe n'a pa été trouvée avec cet id " + studentCreateDTO.getSchoolClassId()));
        }
        Set<Parent> parents = new HashSet<>();
        if (studentCreateDTO.getParentIds() != null) {
            parents = parentRepository.findAllById(studentCreateDTO.getParentIds());

        }

        Set<AnneeScolaire> historique = new HashSet<>();
        if (studentCreateDTO.getAnneeScolaireIds() != null) {
            historique = anneeScolaireRepository.findAllById(studentCreateDTO.getAnneeScolaireIds());
        }
        User user = new User();
        user.setUserName(new UserName(studentCreateDTO.getUser().getUserName().getFirstName(), studentCreateDTO.getUser().getUserName().getLastName()));
        user.setUserEmail(new Email(studentCreateDTO.getUser().getUserEmail().getEmail()));
        Student student = new Student();
        student.setUser(studentCreateDTO.getUser());
        student.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        student.setDateDeNaissance(studentCreateDTO.getDateDeNaissance());
        student.setEtablissement(etablissement);
        student.setSchoolClass(schoolClass);
        student.setAge(CalculateUserAge.calculateAge(studentCreateDTO.getDateDeNaissance()));
        student.setParents(parents);
        student.setEtat(studentCreateDTO.getEtat());
        student.setHistoriqueScolaires(historique);
        // Émettre l'événement
        eventPublisher.publishEvent(new StudentCreatedEvent(
                student.getStudentId(),
                student.getUser().getUserName().getFullName(),
                student.getSchoolClass() != null ? student.getSchoolClass().getSchoolClassId() : null,
                student.getSchoolClass() != null ? student.getSchoolClass().getName() : "Non assigné"
        ));
        return studentRepository.save(student);
    }

    /**
     *
     * @param id , student id
     * @return a student othewise thow an EntityNotFoundException
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public Student getStudentById(Long id) {
        return studentRepository.findByStudentId(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
    }

    /**
     *
     * @param id      student's id
     * @param updated ,student updated
     * @return a student update
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public Student updateStudent(Long id, Student updated) {
        Student existingStudent = studentRepository.findByStudentId(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
        existingStudent.getUser().getUserName().setFirstName(updated.getUser().getUserName().getFirstName());
        existingStudent.getUser().getUserName().setLastName(updated.getUser().getUserName().getLastName());

        Student savedStudent = studentRepository.save(existingStudent);


        Long newClassId = savedStudent.getSchoolClass() != null ?
                savedStudent.getSchoolClass().getSchoolClassId() : null;

        Long oldClassId = existingStudent.getSchoolClass() != null ?
                existingStudent.getSchoolClass().getSchoolClassId() : null;
        if (oldClassId != null && newClassId != null && !oldClassId.equals(newClassId)) {
            eventPublisher.publishEvent(new StudentUpdatedEvent(
                    id,
                    oldClassId,
                    newClassId,
                    savedStudent.getUser().getUserName().getFullName(), true

            ));
        }
        return savedStudent;
    }

    /**
     *
     * @param id student id to be deleted
     */
    @CacheEvict(value = "student-pages", allEntries = true)
    @Transactional
    public void deleteStudentById(Long id) {
        if (!studentRepository.existsByStudentId(id)) {
            throw new EntityNotFoundException("Student not found with id " + id);
        }

        Student student = studentRepository.findByStudentId(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Long classId = student.getSchoolClass() != null ? student.getSchoolClass().getSchoolClassId() : null;
        String className = student.getSchoolClass() != null ? student.getSchoolClass().getName() : "N/A";
        eventPublisher.publishEvent(new StudentDeletedEvent(
                id,
                student.getUser().getUserName().getFullName(),
                classId,
                className
        ));
        studentRepository.deleteByStudentId(id);
    }

    public Page<Student> search(Specification<Student> spec, Pageable pageable) {
        // Si spec est null, on le remplace par une spécification qui ne filtre rien
        Specification<Student> specification = spec == null ? Specification.where((root, query, cb) -> null) : spec;
        return studentRepository.search(specification, pageable);
    }

}
