package com.techprj.banking.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.techprj.banking.dto.UserProfileDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDTODeserializer extends JsonDeserializer<UserProfileDTO[]> {

    @Override
    public UserProfileDTO[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        List<UserProfileDTO> userProfileDTOList = new ArrayList<>();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            UserProfileDTO userProfileDTO = jsonParser.readValueAs(UserProfileDTO.class);
            userProfileDTOList.add(userProfileDTO);
        }
        return userProfileDTOList.toArray(new UserProfileDTO[0]);
    }
	
//	@Override
//	public List<UserProfileDTO> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
//	        throws IOException {
//	    List<UserProfileDTO> userProfileDTOList = new ArrayList<>();
//	    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
//	        UserProfileDTO userProfileDTO = new UserProfileDTO();
//	        userProfileDTO.setIdUserProfile(jsonParser.readValueAs(Long.class));
//	        userProfileDTO.setAuthUserDTO(jsonParser.readValueAs(AuthUserDTO.class));
//	        userProfileDTO.setFirstName(jsonParser.readValueAs(String.class));
//	        userProfileDTO.setMiddleName(jsonParser.readValueAs(String.class));
//	        userProfileDTO.setLastName(jsonParser.readValueAs(String.class));
//	        userProfileDTO.setMobile(jsonParser.readValueAs(Long.class));
//	        userProfileDTO.setEmail(jsonParser.readValueAs(String.class));
//	        userProfileDTO.setAddressDTO(jsonParser.readValueAs(AddressDTO.class));
//	        userProfileDTO.setCustomerSince(jsonParser.readValueAs(String.class));
//	        userProfileDTOList.add(userProfileDTO);
//	    }
//	    return userProfileDTOList;
//	}

}
