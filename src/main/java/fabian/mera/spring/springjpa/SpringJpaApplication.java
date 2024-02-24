package fabian.mera.spring.springjpa;

import fabian.mera.spring.springjpa.entities.Person;
import fabian.mera.spring.springjpa.repositories.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SpringJpaApplication implements CommandLineRunner {

    private final PersonRepository personRepository;

    public SpringJpaApplication(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //findOne();
        //create();
        update();
    }

    public void findOne() {
        //personRepository.findOne(2L).ifPresent(System.out::println);
        //personRepository.findOneLikeName("Jos").ifPresent(System.out::println);
        personRepository.findByNameContaining("Jos").ifPresent(System.out::println);
        /*Person person = null;
        Optional<Person> personOptional=  personRepository.findById(9L);
        if(personOptional.isPresent()){
            person = personOptional.get();
        } else {
            throw new IllegalArgumentException("No existe la persona con el id ingresado");
        }
        System.out.println(person);
    }

        Person person = personRepository.findById(9L).orElseThrow(() -> new IllegalArgumentException("No existe la persona con el id ingresado"));
        System.out.println(person);
        */
    }

    public void list() {
        //List<Person> persons =personRepository.findAll();
        List<Person> persons = personRepository.findByProgrammingLanguage("Python");
        //List<Person> persons2 = personRepository.findBylastname("Mera");
        List<Person> persons3 = personRepository.buscarByProgrammingLanguageJPQL("Python");
        List<Object[]> personValues = personRepository.obtenerPersonaDataJPQL();
        List<Object[]> personLenguaje = personRepository.obtenerPersonaLenguajeJPQL("Python");
        //if(persons2.isEmpty()){
        //    throw new IllegalArgumentException("No person found");
        //}

        //persons2.forEach(System.out::println);
        //personValues.stream().forEach(person->{
        // System.out.println("Name: "+person[0]);
        //System.out.println("Lastname: "+person[1]);

        //});
        //persons.forEach(System.out::println);
        //persons3.forEach(System.out::println);
        personLenguaje.forEach(person -> {
            System.out.println("Name: " + person[0]);
        });
    }

    @Transactional
    public void create() {
        Person person = new Person(null, "Fabian", "Mera", "Java");
        Person personNew = personRepository.save(person);
        System.out.println(personNew);
    }

    @Transactional
    public void update() {
        updatePerson();
    }

    private void updatePerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id de la persona a actualizar");
        Long id = scanner.nextLong();
        Optional<Person> personOptional = personRepository.findById(id);
        personOptional.ifPresent(person -> {
            System.out.println("Ingrese el nuevo nombre");
            person.setName(scanner.next());
            System.out.println("Ingrese el nuevo apellido");
            person.setLastName(scanner.next());
            System.out.println("Ingrese el nuevo lenguaje de programacion");
            person.setProgrammingLanguage(scanner.next());
            Person personDB = personRepository.save(person);
            System.out.println("Persona actualizada" + personDB);
        });
    }
}
