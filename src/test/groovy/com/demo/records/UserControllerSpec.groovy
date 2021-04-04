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

    static final def USERS_LIST_TYPE = new ParameterizedTypeReference<List<UserDto>>() {}

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    UserRepository userRepository

    def 'should get all users'() {
        given:
        def request = RequestEntity.get("/users").accept(MediaType.APPLICATION_JSON).build()

        when:
        def response = restTemplate.exchange(request, USERS_LIST_TYPE)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().size() == 10
    }

    def 'should find user by id'() {
        given:
        def user = new ArrayList<>(userRepository.all).shuffled().first().toDto()
        def request =
            RequestEntity.get("/users/{userId}", user.id()).accept(MediaType.APPLICATION_JSON).build()

        when:
        def response = restTemplate.exchange(request, UserDto.class)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody() == user
    }
}
