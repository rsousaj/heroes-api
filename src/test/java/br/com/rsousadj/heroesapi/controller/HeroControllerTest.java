package br.com.rsousadj.heroesapi.controller;

import br.com.rsousadj.heroesapi.document.Hero;
import br.com.rsousadj.heroesapi.exception.HeroNotFoundException;
import br.com.rsousadj.heroesapi.repository.HeroRepository;
import br.com.rsousadj.heroesapi.service.HeroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.rsousadj.heroesapi.constants.HeroesConstant.*;
import static br.com.rsousadj.heroesapi.utils.JsonConvertionUtils.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class HeroControllerTest {

    @MockBean
    HeroService heroService;

    @MockBean
    HeroRepository heroRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void whenGETisCalledThenHeroesReturned() {
        // given
        Hero expectedHero = Hero.builder().name("Test Name").universe("Test Universe").films(1).build();
        List<Hero> expectedListHeroes = Arrays.asList(expectedHero);

        // when
        when(heroService.findAll()).thenReturn(Flux.fromIterable(expectedListHeroes));

        // then
        webTestClient
                .get()
                .uri(HEROES_ENDPOINT)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(asJsonString(expectedListHeroes));

    }

    @Test
    void whenGETIsCalledWithValidID() throws HeroNotFoundException {
        // given
        Hero expectedHero = Hero.builder().id("1").name("Test Name").universe("Test Universe").films(1).build();

        // when
        when(heroService.findById(expectedHero.getId())).thenReturn(Mono.just(expectedHero));

        // then
        webTestClient
                .get()
                .uri(HEROES_ENDPOINT + "/" + expectedHero.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(asJsonString(expectedHero));
    }

    @Test
    void whenGETIsCalledWithInvalidIDThenNotFoundStatusIsReturned() throws HeroNotFoundException {
        // given
        String INVALID_ID = "invalid id";

        // when
        when(heroService.findById(INVALID_ID)).thenThrow(HeroNotFoundException.class);

        // then
        webTestClient
                .get()
                .uri(HEROES_ENDPOINT + "/" + INVALID_ID)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void whenPOSTisCalledWithValidHeroThenCreatedHeroReturned() {
        // given
        Hero expectedHero = Hero.builder().id("1").name("Test Name").universe("Test Universe").films(1).build();

        // when
        when(heroService.save(expectedHero)).thenReturn(Mono.just(expectedHero));

        // then
        webTestClient
                .post()
                .uri(HEROES_ENDPOINT)
//                .body(expectedHero, Hero.class)
                .body(BodyInserters.fromValue(expectedHero))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .json(asJsonString(expectedHero));
    }

    @Test
    void whenPOSTIsCalledWithInvalidHeroThenReturnBadRequest() {
        // given
        Hero invalidHero = Hero.builder().build();

        // then
        webTestClient
                .post()
                .uri(HEROES_ENDPOINT)
                .body(BodyInserters.fromValue(invalidHero))
                .exchange()
                .expectStatus().isBadRequest();
    }
}