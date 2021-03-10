package br.com.rsousadj.heroesapi.repository;

import br.com.rsousadj.heroesapi.document.Hero;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface HeroRepository extends CrudRepository<Hero, String> {
}
