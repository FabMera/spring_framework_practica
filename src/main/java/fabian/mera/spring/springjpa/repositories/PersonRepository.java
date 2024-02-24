package fabian.mera.spring.springjpa.repositories;

import fabian.mera.spring.springjpa.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);

    //Busca por iniciales del nombre con el % adelante y atras
    @Query("SELECT p FROM Person p WHERE p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    //busca por el nombre que contenga el parametro
    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String lenguaje);

    List<Person> findBylastname(String lastName);

    //Se puede colocar otro nombre pero se debe especificar el nombre de la tabla en la base de datos
    //Personalizado_?1 es el primer parametro que recibe el metodo si tiene mas parametros se sigue con ?2, ?3, etc
    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1")
    List<Person> buscarByProgrammingLanguageJPQL(String lenguaje);

    @Query("SELECT p.name,p.programmingLanguage FROM Person p")
    List<Object[]> obtenerPersonaDataJPQL();

    @Query("SELECT p.name,p.programmingLanguage FROM Person p WHERE p.programmingLanguage = ?1")
    List<Object[]> obtenerPersonaLenguajeJPQL(String programmingLanguage);
}
