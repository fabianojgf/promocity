package ufc.cmu.promocity.backend.service;

import ufc.cmu.promocity.backend.repository.*;
import ufc.cmu.promocity.backend.model.*;
import ufc.cmu.promocity.backend.service.exceptions.UserNotFoundException;
import ufc.cmu.promocity.backend.service.interfaces.IUserService;
import ufc.cmu.promocity.backend.utils.geographic.GPSPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço para consumir o repositório de dados de Usuário
 * @author armandosoaressousa
 *
 */
@Service
public class UsersService extends AbstractService<User, Long>{

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	protected JpaRepository<User, Long> getRepository(){
		return usersRepository;
	}
	
	public User getUserByUsername(String username) {
		return usersRepository.findByUsername(username);
	}
	
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	
}