package br.com.rsousadj.heroesapi.service;

import br.com.rsousadj.heroesapi.document.Hero;
import br.com.rsousadj.heroesapi.exception.HeroNotFoundException;
import br.com.rsousadj.heroesapi.repository.HeroRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {

    @Mock
    HeroRepository heroRepository;

    @InjectMocks
    HeroService heroService;

    @Test
    void whenFindAllIsCalledThenReturnAListOfBeers() {
        // given
        Hero hero1 = Hero.builder().id("1").name("Batman").universe("DC Comics").films(27).build();
        Hero hero2 = Hero.builder().id("2").name("SuperMan").universe("DC Comics").films(20).build();

        // when
        when(heroRepository.findAll()).thenReturn(Arrays.asList(hero1 , hero2));

        // then
        StepVerifier
                .create(heroService.findAll())
                .expectNext(hero1)
                .expectNext(hero2)
                .expectComplete()
                .verify();
    }

    @Test
    void whenFindByIdGivenValidIdThenReturnHero() throws HeroNotFoundException {
        // given
        Hero expectedHero = Hero.builder().id("1").name("Batman").universe("DC Comics").films(27).build();

        // when
        when(heroRepository.findById(expectedHero.getId())).thenReturn(Optional.of(expectedHero));

        // then
        StepVerifier
                .create(heroService.findById(expectedHero.getId()))
                .expectNext(expectedHero)
                .expectComplete()
                .verify();
    }

    @Test
    void whenFindByIdGivenInvalidIdThenThrowsHeroNotFoundException() {
        //given
        String INVALID_ID = "1";

        // when
        when(heroRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(HeroNotFoundException.class, () -> heroService.findById(INVALID_ID));
    }
}
