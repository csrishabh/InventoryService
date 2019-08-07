package com.mongo.demo.repo;

import java.util.List;

import com.mongo.demo.document.Person;

public interface PersonRepoCustom {

	 List<Person> findByNameStartingWith(String regexp, String type);
}
