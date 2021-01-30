package example.micronaut;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2) // <1>
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> { // <2>

    @Transactional
    RefreshTokenEntity save(@NonNull @NotBlank String username,
                            @NonNull @NotBlank String refreshToken,
                            @NonNull @NotNull Boolean revoked); // <3>

    Optional<RefreshTokenEntity> findByRefreshToken(@NonNull @NotBlank String refreshToken); // <4>

    long updateByUsername(@NonNull @NotBlank String username,
                          @NonNull @NotNull Boolean revoked); // <5>
}