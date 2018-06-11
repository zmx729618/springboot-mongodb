package org.zmx.springboot.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.zmx.springboot.domain.HotWordIndex;

@Repository
public interface HotWordRepository extends MongoRepository<HotWordIndex,String>{

}
