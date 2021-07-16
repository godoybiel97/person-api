package one.digitalinnovation.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.repository.PersonRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    public MessageResponseDTO createPerson(@Valid Person person) {
        Person savedPerson = personRepository.save(person);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }

    public List<Person> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream().collect(Collectors.toList());
    }

    public Person findById(Long id) throws PersonNotFoundException {
        Person verifiedPerson = verifyIfExists(id);
        return verifiedPerson;
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, @Valid Person person) throws PersonNotFoundException {
        verifyIfExists(id);
        Person updatedPerson = personRepository.save(person);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO.builder().message(message + id).build();
    }
}