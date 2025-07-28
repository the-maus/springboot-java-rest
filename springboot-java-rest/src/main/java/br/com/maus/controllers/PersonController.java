package br.com.maus.controllers;

import br.com.maus.controllers.docs.PersonControllerDocs;
import br.com.maus.data.dto.PersonDTO;
import br.com.maus.unittests.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonService service;

    @GetMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    // when there's no value then method is returned by default when accessing /person
    @GetMapping(produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @PostMapping(
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO create(@RequestBody PersonDTO person) {
        return service.create(person);
    }

    @PutMapping(
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
