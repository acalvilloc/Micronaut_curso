package example.micronaut;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;

@Client(BintrayConfiguration.BINTRAY_API_URL) 
public interface BintrayClient {
    //Propiedades definidas en el arhivo de configuración yml
    @Get("/api/${bintray.apiversion}/repos/${bintray.organization}/${bintray.repository}/packages") 
    Flowable<BintrayPackage> fetchPackages(); 
}