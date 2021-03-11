package br.com.rsousadj.heroesapi.controller;

import br.com.rsousadj.heroesapi.document.Hero;
import br.com.rsousadj.heroesapi.exception.HeroNotFoundException;
import br.com.rsousadj.heroesapi.service.HeroService;
import com.amazonaws.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static br.com.rsousadj.heroesapi.constants.HeroesConstant.HEROES_ENDPOINT;

@RestController
@Slf4j
@RequestMapping(HEROES_ENDPOINT)
public class HeroController {

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping
    public Flux<Hero> getAllHeroes() {
        log.info("Requesting the list of all heroes");

        return heroService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Hero>> getHeroById(@PathVariable String id) throws HeroNotFoundException {
        log.info("Requesting hero by ID: {}", id);

        return heroService.findById(id)
                .map((hero) -> new ResponseEntity<>(hero, HttpStatus.OK));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Hero> saveHero(@RequestBody @Valid Hero hero) {
        log.info("Requesting hero creation: {}", hero.toString());

        return heroService.save(hero);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<HttpStatus> deleteById(@PathVariable String id) {
        heroService.deleteById(id);
        log.info("deleting a hero with id {}", id);
        return Mono.justOrEmpty(HttpStatus.NO_CONTENT);
    }
}
