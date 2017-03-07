package br.com.alois.solution.configuration.component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.com.alois.domain.entity.user.User;
import br.com.alois.solution.domain.repository.IUserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{

	@Autowired
	IUserRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user = this.userRepository.findUserByUsernameAndPassword(username, password);

		if(user != null && (user.getUsername().equals(username) && user.getPassword().equals(password))){
			return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
