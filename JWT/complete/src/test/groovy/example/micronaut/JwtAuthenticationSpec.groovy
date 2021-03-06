package example.micronaut

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest 
class JwtAuthenticationSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client 

    void 'Accessing a secured URL without authenticating returns unauthorized'() {
        when:
        client.toBlocking().exchange(HttpRequest.GET('/', )) 

        then:
        HttpClientResponseException e = thrown()
        e.status == HttpStatus.UNAUTHORIZED
    }

    void "upon successful authentication, a Json Web token is issued to the user"() {
        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("sherlock", "asa8d946qwrwuivoashdiuqewas98d842121e98165ads")
        HttpRequest request = HttpRequest.POST('/login', creds) 
        HttpResponse<BearerAccessRefreshToken> rsp = client.toBlocking().exchange(request, BearerAccessRefreshToken) 

        then: 'the endpoint can be accessed'
        rsp.status == HttpStatus.OK

        when:
        BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body()

        then:
        bearerAccessRefreshToken.username == 'sherlock'
        bearerAccessRefreshToken.accessToken

        and: 'the access token is a signed JWT'
        JWTParser.parse(bearerAccessRefreshToken.accessToken) instanceof SignedJWT

        when: 'passing the access token as in the Authorization HTTP Header with the prefix Bearer allows the user to access a secured endpoint'
        String accessToken = bearerAccessRefreshToken.accessToken
        HttpRequest requestWithAuthorization = HttpRequest.GET('/' )
                .accept(MediaType.TEXT_PLAIN)
                .bearerAuth(accessToken) 
        HttpResponse<String> response = client.toBlocking().exchange(requestWithAuthorization, String)

        then:
        response.status == HttpStatus.OK
        response.body() == 'sherlock' 
    }
}