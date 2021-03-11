package br.com.rsousadj.heroesapi.service;

import br.com.rsousadj.heroesapi.document.Hero;
import br.com.rsousadj.heroesapi.exception.HeroNotFoundException;
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

    public Mono<Hero> findById(String id) throws HeroNotFoundException {
        return Mono.justOrEmpty(verifyExists(id));
    }

    public Mono<Hero> save(Hero hero) {
        return Mono.just(this.heroRepository.save(hero));
    }

    public Mono<Boolean> deleteById(String id) {
        heroRepository.deleteById(id);
        return Mono.justOrEmpty(true);
    }

    private Hero verifyExists(String id) throws HeroNotFoundException {
        return this.heroRepository.findById(id)
                .orElseThrow(() -> new HeroNotFoundException(id));
    }
}
