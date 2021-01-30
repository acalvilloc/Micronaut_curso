package example.micronaut

import javax.inject.Singleton

@Singleton 
class AuthoritiesFetcherService implements AuthoritiesFetcher {

    protected final UserRoleGormService userRoleGormService

    AuthoritiesFetcherService(UserRoleGormService userRoleGormService) {  
        this.userRoleGormService = userRoleGormService
    }

    @Override
    List<String> findAuthoritiesByUsername(String username) {
        userRoleGormService.findAllAuthoritiesByUsername(username)
    }
}