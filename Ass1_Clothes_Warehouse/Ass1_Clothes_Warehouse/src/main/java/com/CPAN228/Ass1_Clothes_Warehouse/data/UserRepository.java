package com.CPAN228.Ass1_Clothes_Warehouse.data;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CPAN228.Ass1_Clothes_Warehouse.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    List<User> findByRole(User.Role role);
}
