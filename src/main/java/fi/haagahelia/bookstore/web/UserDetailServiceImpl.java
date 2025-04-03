package fi.haagahelia.bookstore.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;

/**
 * This class is used by Spring Security to authenticate and authorize users.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AppUserRepository repository;

    // Constructor Injection
    public UserDetailServiceImpl(AppUserRepository appUserRepository) {
        this.repository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser currUser = repository.findByUsername(username);

        if (currUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                currUser.getUsername(),
                currUser.getPasswordHash(),
                AuthorityUtils.createAuthorityList(currUser.getRole()));
    }
}