package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.rest.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController extends EtnRestController {

	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/frameworks/all")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@GetMapping("/frameworks/find/{id}")
	public Iterable<JavaScriptFramework> frameworksById(@PathVariable Long id) {
		return repository.findAllById(Collections.singleton(id));
	}

	@GetMapping("/frameworks/find_one/{id}")
	public Optional<JavaScriptFramework> frameworkById(@PathVariable Long id) {
		return repository.findById(id);
	}


	@GetMapping("/frameworks/findByName")
	public Iterable<JavaScriptFramework> frameworkByName(@RequestParam String name) {
		Optional<ArrayList<JavaScriptFramework>> frameworks = repository.findByName(name);
		return frameworks.orElse(null);
	}

	@GetMapping("/frameworks/findByHype")
	public Iterable<JavaScriptFramework> frameworkByHype(@RequestParam double hype) {
		Optional<ArrayList<JavaScriptFramework>> frameworks = repository.findByHype(hype);
		return frameworks.orElse(null);
	}

	@PostMapping(path = "/frameworks/add", consumes = "application/json")
	public ResponseEntity<Errors> frameworks(@RequestBody JavaScriptFramework framework) {
		try {
			repository.save(framework);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				return handleDataIntegrityViolationException(framework);
			}
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/frameworks/delete/{id}")
	public void frameworks(@PathVariable Long id){
		repository.deleteById(id);
	}

	@PutMapping("/frameworks/change")
	public void changeFrameworks(@RequestParam Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String version, @RequestParam(required = false) Long deprecationDate, @RequestParam(required = false) Double hype){
		Optional<JavaScriptFramework> framework = repository.findById(id);

		if (framework.isPresent()) {
			if (name != null && !name.trim().isEmpty())
				framework.get().setName(name);
			if (version != null && !version.trim().isEmpty())
				framework.get().setVersion(version);
			if (deprecationDate != null)
				framework.get().setDeprecationDate(deprecationDate);
			if (hype != null)
				framework.get().setHype(hype);
		}
		JavaScriptFramework fm = framework.get();
		repository.save(framework.get());
	}

}
