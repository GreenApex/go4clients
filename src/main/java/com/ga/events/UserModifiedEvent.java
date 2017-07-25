package com.ga.events;

import com.ga.entity.Employee;

import java.io.Serializable;

public class UserModifiedEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Employee employee;

    public UserModifiedEvent(Employee e) {
        this.employee = e;
    }

    public Employee getEmployee() {
        return employee;
    }
}
