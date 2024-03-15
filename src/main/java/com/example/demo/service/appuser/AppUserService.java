package com.example.demo.service.appuser;

import com.example.demo.model.AppRole;
import com.example.demo.model.AppUser;
import com.example.demo.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements IAppUserService, UserDetailsService {
    @Autowired
    private AppUserRepo userRepo;
    @Override
    public Iterable<AppUser> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public AppUser save(AppUser appUser) {
        return userRepo.save(appUser);
    }

    @Override
    public void remove(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByName(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(appUser.getAppRole());
        UserDetails userDetails = new User(
                appUser.getName(),
                appUser.getPassword(),
                grantedAuthorities
        );
        return userDetails;

    }
}
