package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier getSupplierById(Integer id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.orElse(null);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier updateSupplier(Integer id, Supplier supplierDetails) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier supplier = optionalSupplier.get();
            supplier.setBranchLoc(supplierDetails.getBranchLoc());
            supplier.setBranchManager(supplierDetails.getBranchManager());
            supplier.setBranchEmail(supplierDetails.getBranchEmail());
            supplier.setBranchPhoneNo(supplierDetails.getBranchPhoneNo());
            supplier.setBranchPassword(supplierDetails.getBranchPassword());
            return supplierRepository.save(supplier);
        }
        return null;
    }

    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
    }
}
