package hu.doravar.logistics.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.doravar.logistics.model.LogisticsUser;
import hu.doravar.logistics.repository.UserRepository;

@Service
public class LogisticsUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LogisticsUser logisticsUser = userRepository.findById(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));
		
		return new User(username, logisticsUser.getPassword(), 
				logisticsUser.getRoles().stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()));
	}

}
