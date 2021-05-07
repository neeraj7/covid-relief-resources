package com.covid.relief;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class TestMainClass {
	public static void main(String[] args) {
		
		LocalDate ld = LocalDate.now().minusYears(5);
		
		Employee emp = new Employee();
		emp.salary = 123000;
		emp.startDate = LocalDate.of(2018, 5, 3);
		
		Employee emp1 = new Employee();
		emp1.salary = 110000;
		emp1.startDate = LocalDate.of(2015, 5, 3);
		
		Employee emp2 = new Employee();
		emp2.salary = 103000;
		emp2.startDate = LocalDate.of(2017, 5, 3);
		
		Employee emp3 = new Employee();
		emp3.salary = 133000;
		emp3.startDate = LocalDate.of(2019, 5, 3);
		
		List<Employee> emps = new ArrayList<>();
		emps.add(emp);
		emps.add(emp1);
		emps.add(emp2);
		emps.add(emp3);
		
		emps.forEach(e -> System.out.println(e));
		
		emps.sort((e1, e2) -> {
			if(e1.salary > e2.salary)
				return -1;
			else if(e1.salary == e2.salary)
				return 0;
			else
				return 1;
		});
		System.out.println("AFTER SORT");
		emps.forEach(e -> System.out.println(e));
		
		List<Employee> collect = emps.stream().filter(e -> (e.salary > 100000) && LocalDate.now().minusYears(5).isBefore(e.startDate))
		.limit(10)
		.collect(Collectors.toList());
		
		System.out.println("RESULT");
		
		collect.forEach(e -> System.out.println(e));
	}
}

class Employee implements Comparable<Employee> {
	int salary;
	LocalDate startDate;
	
	@Override
	public int compareTo(Employee o) {
		if(this.salary > o.salary)
			return 1;
		else if(this.salary == o.salary)
			return 0;
		else
			return -1;
	}

	@Override
	public String toString() {
		return "Employee [salary=" + salary + ", startDate=" + startDate + "]";
	}
}
