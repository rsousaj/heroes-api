package br.com.rsousadj.heroesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HeroNotFoundException extends Exception {

    public HeroNotFoundException(String id) {
        super(String.format("Hero with ID %s not found!", id));
    }
}
