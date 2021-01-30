package example.micronaut

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class DeclarativeHttpClientWithJwtSpec extends Specification {

    @Inject
    AppClient appClient 

    def "Verify JWT authentication works with declarative @Client"() {
        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("sherlock", "asa8d946qwrwuivoashdiuqewas98d842121e98165ads")
        BearerAccessRefreshToken loginRsp = appClient.login(creds) 

        then:
        loginRsp
        loginRsp.accessToken
        JWTParser.parse(loginRsp.accessToken) instanceof SignedJWT

        when:
        String msg = appClient.home("Bearer ${loginRsp.accessToken}") 

        then:
        msg == 'sherlock'
    }
}