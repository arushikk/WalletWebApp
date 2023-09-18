package com.Example.Wallet.Project;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByPhoneNumber(String phoneno);
}
