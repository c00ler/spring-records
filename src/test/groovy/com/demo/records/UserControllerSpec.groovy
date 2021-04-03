package com.demo.records

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    UserRepository userRepository

    def 'should get all users'() {
        given:
        def request = RequestEntity.get("/users").accept(MediaType.APPLICATION_JSON).build()

        when:
        def response = restTemplate.exchange(request, new ParameterizedTypeReference<List<User>>() {})

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().size() == 10
    }

    def 'should find user by id'() {
        given:
        def users = userRepository.getAll()
        def user = new ArrayList<>(users).get(new Random().nextInt(users.size()))
        def request =
            RequestEntity.get("/users/{userId}", user.id()).accept(MediaType.APPLICATION_JSON).build()

        when:
        def response = restTemplate.exchange(request, User.class)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody() == user
    }
}