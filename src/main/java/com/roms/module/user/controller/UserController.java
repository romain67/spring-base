package com.roms.module.user.controller;

import java.util.Collection;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.roms.module.user.domain.dto.UserCreateDto;
import com.roms.module.user.domain.model.User;
import com.roms.module.user.service.UserService;
import com.roms.library.http.exception.NotFoundException;

@RestController("userController")
@RequestMapping("/user")
public class UserController {

    @Autowired
	private UserService userService;

    /**
     * List users
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<User> getAll() {
    	return userService.findAll();
    }

    /**
     * Get user
     * @param id
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> get(@PathVariable("id") long id) {

		User user = this.userService.find(id);

		if (user == null) {
			throw new NotFoundException("User id '" + id + "' can not be found");
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

    /**
     * Create user
     * @param userDto
     */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> post(@Validated @RequestBody UserCreateDto userDto)
	{
		User user = userService.create(userDto);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

    /**
     * Update user
     * @param userDto
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void put(@Validated @RequestBody UserCreateDto userDto) 
	{
		
	}

    /**
     * Delete User
     * @param id
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		try {
			userService.delete(id);
		} catch (ObjectNotFoundException e) {
			throw new NotFoundException("User id '" + id + "' can not be found");
		}
	}
		
}
