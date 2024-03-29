package br.com.catapan.salaobeleza.infrastructure.web.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.catapan.salaobeleza.domain.cliente.Cliente;
import br.com.catapan.salaobeleza.domain.profissional.Profissional;
import br.com.catapan.salaobeleza.domain.usuario.Usuario;

@SuppressWarnings("serial")
public class LoggedUser implements UserDetails {
	
	private Usuario usuario;
	private Role role;
	private Collection<? extends GrantedAuthority> roles;
	
	public LoggedUser(Usuario usuario) {
		this.usuario = usuario;
		
		Role role;
		
		if (usuario instanceof Cliente) {
			role = Role.CLIENTE;
		} 
		else if (usuario instanceof Profissional) {
			role = Role.PROFISSIONAL;
		} 
		else {
			throw new IllegalStateException("O tipo de usu�rio n�o � v�lido");
		}
		
		this.role = role;
		this.roles = List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Role getRole() {
		return role;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}
