package com.techprj.banking.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.techprj.banking.dto.AddressDTO;
import com.techprj.banking.dto.AuthUserDTO;
import com.techprj.banking.dto.LoginLogDTO;
import com.techprj.banking.dto.UserProfileDTO;
import com.techprj.banking.entity.Address;
import com.techprj.banking.entity.LoginLog;
import com.techprj.banking.entity.UserProfile;
import com.techprj.banking.repo.LoginLogRepo;
import com.techprj.banking.repo.UserProfileRepo;

@Service
public class INTServiceDAOImpl implements INTServiceDAO {
	
	@Autowired
	UserProfileRepo userProfileRepo;
	
	@Autowired
	LoginLogRepo loginLogRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public UserProfileDTO addUser(UserProfileDTO userProfileDTO) {

		userProfileDTO.setCustomerSince(LocalDate.now());
		
		//create account then add it before saving
		
		UserProfile up = userProfileRepo.saveAndFlush(modelMapper.map(userProfileDTO, UserProfile.class));

		//System.out.println(up.getCustomerSince());
		AuthUserDTO audto = modelMapper.map(up.getAuthUser(), AuthUserDTO.class);
		AddressDTO adddto = modelMapper.map(up.getAddress(), AddressDTO.class);
		
		UserProfileDTO updto = modelMapper.map(up, UserProfileDTO.class);
		
		updto.setAuthUserDTO(audto);
		updto.setAddressDTO(adddto);
		
		return updto;
		
	}

	@Override
	public UserProfileDTO getProfile(String emailid) {
		System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		UserProfile up = userProfileRepo.findByEmail(emailid);
		System.out.println("/////////////////////////////////////");
		System.out.println(up);
	
		long id = up.getIdUserProfile();
		
		Object[] acc = restTemplate.getForObject("http://localhost:8083/api/getaccounts/"+id, Object[].class);
	 
		up.setAccounts(acc);
			 
		//System.out.println(up.getAccounts()[0]);
	
		//UserProfile up1 = userProfileRepo.save(up);
		
		AuthUserDTO audto = modelMapper.map(up.getAuthUser(), AuthUserDTO.class);
		AddressDTO adddto = modelMapper.map(up.getAddress(), AddressDTO.class);
		
		UserProfileDTO updto = modelMapper.map(up, UserProfileDTO.class);
		
		updto.setAuthUserDTO(audto);
		updto.setAddressDTO(adddto);
		System.out.println(updto); 
		return updto;
	}
	
	@Override
	public UserProfileDTO getProfileById(Long id) {

		Optional<UserProfile> up = userProfileRepo.findById(id);
		System.out.println(up.get());
		
		Object[] acc = restTemplate.getForObject("http://localhost:8083/api/getaccounts/"+id, Object[].class);
		
		up.get().setAccounts(acc);
		
		if(up.isPresent()) {
			
			AuthUserDTO audto = modelMapper.map(up.get().getAuthUser(), AuthUserDTO.class);
			AddressDTO adddto = modelMapper.map(up.get().getAddress(), AddressDTO.class);
			
			UserProfileDTO updto = modelMapper.map(up, UserProfileDTO.class);
			
			updto.setAuthUserDTO(audto);
			updto.setAddressDTO(adddto);
			
			//System.out.println(updto);
			return updto;
			
		}
		
		return null;
		
	}
	
	@Override
	public UserProfileDTO updateUser(Long userid, Map<Object, Object> fields) {
//		System.out.println(11111111);
//		System.out.println(fields.get("mobile"));
		
		if(fields.get("mobile") != null) {
			Object mobileNo = fields.get("mobile");
			//System.out.println(mobileNo);
			Long longMobile = Long.parseLong(mobileNo.toString());
			//System.out.println(longMobile);
			fields.put("mobile", longMobile);
		}
					
		Optional<UserProfile> up = userProfileRepo.findById(userid);
		
//		System.out.println("**************************updateuser");
//		System.out.println(111111);
//		System.out.println(up.get());
		
		UserProfileDTO updto = modelMapper.map(up.get(), UserProfileDTO.class);
		
		AuthUserDTO authDTO = modelMapper.map(up.get().getAuthUser(), AuthUserDTO.class);
		
		AddressDTO adto = modelMapper.map(up.get().getAddress(), AddressDTO.class);
		
		updto.setAddressDTO(adto);
		
		updto.setAuthUserDTO(authDTO);
		
//		System.out.println(22222222);
//		System.out.println(updto);
		System.out.println("**************************updateuser");
		if(up.isPresent()) {
			
			fields.forEach((key, value) -> {
//				System.out.println(key);
//				System.out.println(value);
				if (key.equals("addressDTO")) {
				    AddressDTO addressDTO = updto.getAddressDTO();
				    if (value instanceof Map) {
				        @SuppressWarnings("unchecked")
				        Map<String, Object> addressFields = (Map<String, Object>) value;

				        addressFields.forEach((addressKey, addressValue) -> {
				            // Check if the new value is not an empty string
				            if (addressValue instanceof String && !((String) addressValue).isEmpty()) {
				                // Update the fields of the AddressDTO
				                Field field = ReflectionUtils.findRequiredField(AddressDTO.class, addressKey);
				                field.setAccessible(true);
				                ReflectionUtils.setField(field, addressDTO, addressValue);
				            }
				        });
				    }
				}

 

				if (key != "addressDTO"){
				
					Field field1 = ReflectionUtils.findRequiredField(UserProfileDTO.class, (String) key);
					field1.setAccessible(true);
					ReflectionUtils.setField(field1, updto, value);
				
				}
				
//				if (key.equals("authUserDTO")) {
//					AuthUserDTO authUserDTO = (AuthUserDTO) value;
//					updto.setAuthUserDTO(authUserDTO);
//				}
			});
		}
		
		UserProfile newUP = userProfileRepo.save(modelMapper.map(updto, UserProfile.class));
//		System.out.println(3333333);
//		System.out.println(newUP);
		
		UserProfileDTO upDTO = modelMapper.map(newUP, UserProfileDTO.class);
		
		AuthUserDTO authdto = modelMapper.map(newUP.getAuthUser(), AuthUserDTO.class);
		
		AddressDTO adddto = modelMapper.map(newUP.getAddress(), AddressDTO.class);
		
		upDTO.setAuthUserDTO(authdto);
		
		upDTO.setAddressDTO(adddto);
//		System.out.println(444444444);
//		System.out.println(upDTO);
		
		return upDTO;
	}

	@Override
	public LoginLogDTO createLog(String emailid) {
		
		UserProfileDTO updto = getProfile(emailid);
		
		//set user profile
		LoginLogDTO lldto = new LoginLogDTO();
		lldto.setUserProfileDTO(updto);
		
		//set IP address
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			lldto.setiP(localhost.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		//set location
		try {
            URL url = new URL("https://ipapi.co/json/");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            String city = jsonNode.get("city").asText();
          
            lldto.setLocation(city);
            //System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		//set date and time
		lldto.setEventTime(LocalDateTime.now());
		
		System.out.println(".............................");

		//System.out.println(lldto.getUserProfileDTO());
		
		LoginLog lolo = loginLogRepo.saveAndFlush(modelMapper.map(lldto, LoginLog.class));
		
		//System.out.println(lolo.getUserProfile());
				
		LoginLogDTO lolodto = modelMapper.map(lolo, LoginLogDTO.class);
		
		lolodto.setUserProfileDTO(updto);
		
		System.out.println(lolodto);
		
		return lolodto;
	}

	@Override
	public List<LoginLogDTO> getLog(String emailid) {
		
		//System.out.println("//////////////////");
		UserProfile up = userProfileRepo.findByEmail(emailid);
		//System.out.println(up);
		
		Long id = up.getIdUserProfile();
		//System.out.println(id);
		List<LoginLog> lolol = loginLogRepo.findAll();
		//System.out.println(lolol.size());
		List<LoginLogDTO> llldto = new ArrayList();
		
		for(LoginLog a: lolol) {
			
			if(a.getUserProfile().getIdUserProfile() == id) {
				
				LoginLogDTO newA = modelMapper.map(a, LoginLogDTO.class);
				
				AuthUserDTO audto = modelMapper.map(up.getAuthUser(), AuthUserDTO.class);
				AddressDTO adddto = modelMapper.map(up.getAddress(), AddressDTO.class);
				
				UserProfileDTO updto = modelMapper.map(up, UserProfileDTO.class);
				
				updto.setAuthUserDTO(audto);
				updto.setAddressDTO(adddto);
				
				newA.setUserProfileDTO(updto);
				
				llldto.add(newA);
				
			}
			
		}
		
		return llldto;
		
	}



}

