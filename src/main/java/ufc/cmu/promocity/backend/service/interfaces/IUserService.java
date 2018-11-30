package ufc.cmu.promocity.backend.service.interfaces;

import java.util.List;

import ufc.cmu.promocity.backend.model.User;

public interface IUserService {

	public List<User> getAllUsers();
	public User getUser(Long id);
	public void addUser(User user);
	public void updateUser(Long id, User newUser);
	public User deleteUser(Long id);	
	
}
