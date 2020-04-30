package com.bk.donglt.patient_manager.repository.address;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.address.Block;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends BaseRepository<Block> {
    Block findByName(String name);
    List<Block> findByDistrict_Id(long id);
}
