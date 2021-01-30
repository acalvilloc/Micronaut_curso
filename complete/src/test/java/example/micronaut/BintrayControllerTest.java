package example.micronaut;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxStreamingHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest 
class BintrayControllerTest {

    @Inject
    @Client("/")
    RxStreamingHttpClient client; 

    private static final List<String> expectedProfileNames = Arrays.asList("base", "federation", "function", "function-aws", "service");

    @Test
    public void verifyBintrayPackagesCanBeFetchedWithLowLevelHttpClient() {
        //when:
        HttpRequest request = HttpRequest.GET("/bintray/packages-lowlevel");

        HttpResponse<List<BintrayPackage>> rsp = client.toBlocking().exchange(request, 
                Argument.listOf(BintrayPackage.class)); 

        //then: 'the endpoint can be accessed'
        assertEquals(HttpStatus.OK, rsp.getStatus());   
        assertNotNull(rsp.body()); 

        //when:
        List<BintrayPackage> packages = rsp.body();

        //then:
        for (String name : expectedProfileNames) {
            assertTrue(packages.stream().map(BintrayPackage::getName).anyMatch(name::equals));
        }
    }

    @Test
    public void verifyBintrayPackagesCanBeFetchedWithCompileTimeAutoGeneratedAtClient() {
        //when:
        HttpRequest request = HttpRequest.GET("/bintray/packages");

        Flowable<BintrayPackage> bintrayPackageStream = client.jsonStream(request, BintrayPackage.class); 
        Iterable<BintrayPackage> bintrayPackages = bintrayPackageStream.blockingIterable();

        //then:
        for (String name : expectedProfileNames) {
            assertTrue(StreamSupport.stream(bintrayPackages.spliterator(), false)
                    .map(BintrayPackage::getName)
                    .anyMatch(name::equals));
        }
    }
}