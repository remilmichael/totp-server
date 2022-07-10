package me.remil.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.remil.entity.Role;
import me.remil.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	@Transactional
	public void truncateTable() {
		roleRepository.truncateTable();
	}

	@Override
	public void addRole(Role role) {
		roleRepository.save(role);
	}

}
