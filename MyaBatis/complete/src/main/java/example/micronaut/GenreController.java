package example.micronaut;

import example.micronaut.domain.Genre;
import example.micronaut.genre.GenreRepository;
import example.micronaut.genre.GenreSaveCommand;
import example.micronaut.genre.GenreUpdateCommand;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller("/genres") 
public class GenreController {

    protected final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) { 
        this.genreRepository = genreRepository;
    }

    @Get("/{id}") 
    public Genre show(Long id) {
        return genreRepository
                .findById(id)
                .orElse(null); 
    }

    @Put 
    public HttpResponse<?> update(@Body @Valid GenreUpdateCommand command) { 
        genreRepository.update(command.getId(), command.getName());
        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getId()).getPath()); 
    }

    @Get(value = "/list{?args*}") 
    public List<Genre> list(@Valid ListingArguments args) {
        return genreRepository.findAll(args);
    }

    @Post 
    public HttpResponse<Genre> save(@Body @Valid GenreSaveCommand cmd) {
        Genre genre = genreRepository.save(cmd.getName());

        return HttpResponse
                .created(genre)
                .headers(headers -> headers.location(location(genre.getId())));
    }

    @Delete("/{id}") 
    public HttpResponse<?> delete(Long id) {
        genreRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    protected URI location(Long id) {
        return URI.create("/genres/" + id);
    }

    protected URI location(Genre genre) {
        return location(genre.getId());
    }
}