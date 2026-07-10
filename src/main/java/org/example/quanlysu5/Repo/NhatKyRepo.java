package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Module.NhatKyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NhatKyRepo extends JpaRepository<NhatKyEntity,String>, JpaSpecificationExecutor<NhatKyEntity> {

}
