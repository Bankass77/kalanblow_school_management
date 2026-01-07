package com.kalanblow.school_management.application.usecase.student;

import com.kalanblow.school_management.infrastructure.web.dto.StudentCreateDTO;
import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import com.kalanblow.school_management.model.classe.SchoolClass;
import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.model.parent.Parent;
import com.kalanblow.school_management.model.shared.util.KaladewnUtility;
import com.kalanblow.school_management.model.student.Student;
import com.kalanblow.school_management.service.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreateStudentUseCase {

    private final StudentService studentService;
    private final AnneeScolaireService anneeScolaireService;
    private final SchoolClassService schoolClassService;
    private final EtablissementService etablissementService;
    private final ParentService parentService;

    public CreateStudentUseCase(StudentService studentService, AnneeScolaireService anneeScolaireService, SchoolClassService schoolClassService, EtablissementService etablissementService, ParentService parentService) {
        this.studentService = studentService;
        this.anneeScolaireService = anneeScolaireService;
        this.schoolClassService = schoolClassService;
        this.etablissementService = etablissementService;
        this.parentService = parentService;
    }

    @CacheEvict(value = "students-pages", allEntries = true)
    public Student execute(Student student) {

        Etablissement etablissement = etablissementService.findById(student.getEtablissement().getEtablisementScolaireId());
        SchoolClass schoolClass = null;
        if (student.getSchoolClass() != null) {
            schoolClass = schoolClassService.findSchoolClassById(student.getSchoolClass().getSchoolClassId());
        }
        Set<Parent> parents = new HashSet<>();

        if (!student.getParents().isEmpty()) {
            parents = parentService.findAllById(student.getParents().stream().map(Parent::getParentId).collect(Collectors.toSet()));

        }

        Set<AnneeScolaire> historique = new HashSet<>();
        if (student.getSchoolClass().getAnneeScolaire() != null) {
            historique = Collections.singleton(anneeScolaireService.findById(student.getSchoolClass().getSchoolClassId()));
        }

        student.setHistoriqueScolaires(historique);
        student.setSchoolClass(schoolClass);
        student.setEtablissement(etablissement);
        student.setParents(parents);
        StudentCreateDTO studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setEtat(student.getEtat());
        studentCreateDTO.setUser(student.getUser());
        studentCreateDTO.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        studentCreateDTO.setEtablissementId(student.getEtablissement().getEtablisementScolaireId());
        Set<Long> parentSet = student.getParents().stream().map(Parent::getParentId).collect(Collectors.toCollection(HashSet::new));
        studentCreateDTO.setParentIds(parentSet);
        studentCreateDTO.setSchoolClassId(student.getSchoolClass().getSchoolClassId());
        return studentService.createNewStudent(studentCreateDTO);
    }
}
