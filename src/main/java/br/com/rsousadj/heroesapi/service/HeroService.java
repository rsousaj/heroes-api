package br.com.rsousadj.heroesapi.service;

import br.com.rsousadj.heroesapi.document.Hero;
import br.com.rsousadj.heroesapi.repository.HeroRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroService {

    private final HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Flux<Hero> findAll() {
        return Flux.fromIterable(this.heroRepository.findAll());
    }

    public Mono<Hero> findById(String id) {
        return Mono.justOrEmpty(this.heroRepository.findById(id));
    }

    public Mono<Hero> save(Hero hero) {
        return Mono.justOrEmpty(this.heroRepository.save(hero));
    }

    public Mono<Boolean> deleteById(String id) {
        heroRepository.deleteById(id);
        return Mono.justOrEmpty(true);
    }
}
