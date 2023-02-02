package nic.task.accountingsystem.utility;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends MongoRepository<LoginRecord, String> {
}
