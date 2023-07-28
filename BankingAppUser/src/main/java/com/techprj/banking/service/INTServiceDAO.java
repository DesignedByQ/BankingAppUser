package com.techprj.banking.service;

import java.util.List;
import java.util.Map;

import com.techprj.banking.dto.LoginLogDTO;
import com.techprj.banking.dto.UserProfileDTO;

public interface INTServiceDAO {
	
	UserProfileDTO addUser(UserProfileDTO userProfileDTO);
	
	UserProfileDTO getProfile(String emailid);
	
	LoginLogDTO createLog(String emailid);
	
	List<LoginLogDTO> getLog(String emailid);

	UserProfileDTO getProfileById(Long id);
	
	UserProfileDTO updateUser(Long userid, Map<Object, Object> fields);

}
