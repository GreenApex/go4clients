package com.ga.mapper;

import java.util.List;

import com.ga.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findAllBy(Pageable pageable);

	List<Employee> findByNameLikeIgnoreCase(String nameFilter);

	public Employee findByEmail(String email);
}
