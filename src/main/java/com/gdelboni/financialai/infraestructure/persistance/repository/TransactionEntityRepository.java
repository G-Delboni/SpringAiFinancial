package com.gdelboni.financialai.infraestructure.persistance.repository;

import com.gdelboni.financialai.domain.Category;
import com.gdelboni.financialai.infraestructure.persistance.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByCategory(Category category);
}
