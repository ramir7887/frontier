package com.sber.fortir.controller;

import com.sber.fortir.domain.Hello;
import com.sber.fortir.service.HelloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @PostMapping
    public ResponseEntity<Hello> save(@RequestBody Hello hello) {
        return ResponseEntity.ok(
                helloService.save(
                        hello
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<Hello>> findAll() {
        return ResponseEntity.ok(
                helloService.findAll()
        );
    }
}
