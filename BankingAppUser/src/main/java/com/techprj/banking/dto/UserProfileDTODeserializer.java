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
}
