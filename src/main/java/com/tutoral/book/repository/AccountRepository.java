package com.tutoral.book.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.tutoral.book.document.Account;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

}
