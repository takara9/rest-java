package com.example.appl1.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.appl1.model.Person;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;	
	}
	
	@Override
	public int insertPerson(UUID id, Person person) {
		final String sql = "INSERT INTO person(id,name) VALUES(?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				person.getId(),
				person.getName()
		});
	}

	
	@Override
	public List<Person> selectAllPeople() {
		final String sql = "SELECT id, name FROM person";
		return jdbcTemplate.query(sql,( resultSet, i) -> {
			UUID id = UUID.fromString(resultSet.getString("id"));
			String name = resultSet.getString("name");
			return new Person(id,name);
		});
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		final String sql = "SELECT id, name FROM person WHERE id = ?";
		Person person = jdbcTemplate.queryForObject(
				sql, new Object[]{id},
				( resultSet, i) -> {
			UUID pid = UUID.fromString(resultSet.getString("id"));
			String name = resultSet.getString("name");
			return new Person(pid,name);
		});
		return Optional.ofNullable(person);
	}

	@Override
	public int deletePersonById(UUID id) {
		final String sql = "DELETE FROM person WHERE id = ?";
		return jdbcTemplate.update(sql, new Object[]{id});
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		final String sql = "UPDATE person SET name = ? WHERE id = ?";
		return jdbcTemplate.update(sql,
				new Object[]{person.getName(),id}
				);
	}
	

}
