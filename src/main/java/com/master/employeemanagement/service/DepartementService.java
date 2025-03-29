package com.master.employeemanagement.service;

import com.master.employeemanagement.model.Departement;
import com.master.employeemanagement.repository.DepartementRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartementService {
    @Autowired
    private DepartementRepository departementRepository;

    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    public Optional<Departement> getDepartementById(Long id) {
        return departementRepository.findById(id);
    }

    public Departement addDepartement(String departement) {
        return departementRepository.save(Departement.builder().nom(departement).build());
    }

    public boolean deleteDepartement(Long id) {
        if (departementRepository.existsById(id)) {
            departementRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Departement updateDepartement(Long id, Departement departement) {
        if (departementRepository.existsById(id)) {
            departement.setId(id);
            return departementRepository.save(departement);
        }
        return null;
    }
}
