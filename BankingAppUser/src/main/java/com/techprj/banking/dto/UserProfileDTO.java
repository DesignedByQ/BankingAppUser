package com.techprj.banking.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class UserProfileDTO implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(UserProfileDTO.class.getName());
	
	private Long idUserProfile;
	private AuthUserDTO authUserDTO;
	private String firstName;
	private String middleName;
	private String lastName;
	private Long mobile;
	private String email;
	private AddressDTO addressDTO;
	private LocalDate customerSince;

	private Object[] accounts;
	
	//creates an user then creates acc using user id then adds acc to the user account list
	//update the get user method to serch all accounts with the same user id populate the list
	
	public UserProfileDTO() {
		super();
	}

	public UserProfileDTO(Long idUserProfile, AuthUserDTO authUserDTO, String firstName, String middleName,
			String lastName, Long mobile, String email, AddressDTO addressDTO, LocalDate customerSince,
			Object[] accounts) {
		super();
		this.idUserProfile = idUserProfile;
		this.authUserDTO = authUserDTO;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
		this.addressDTO = addressDTO;
		this.customerSince = customerSince;
		this.accounts = accounts;
	}



	public Long getIdUserProfile() {
		return idUserProfile;
	}

	public void setIdUserProfile(Long idUserProfile) {
		this.idUserProfile = idUserProfile;
	}

	public AuthUserDTO getAuthUserDTO() {
		return authUserDTO;
	}

	public void setAuthUserDTO(AuthUserDTO authUserDTO) {
		this.authUserDTO = authUserDTO;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public LocalDate getCustomerSince() {
		return customerSince;
	}

	public void setCustomerSince(LocalDate customerSince) {
		this.customerSince = customerSince;
	}

	public Object[] getAccounts() {
		return accounts;
	}

	public void setAccounts(Object[] accounts) {
		this.accounts = accounts;
	}
	
	

    @Override
	public String toString() {
		return "UserProfileDTO [idUserProfile=" + idUserProfile + ", authUserDTO=" + authUserDTO + ", firstName="
				+ firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", mobile=" + mobile
				+ ", email=" + email + ", addressDTO=" + addressDTO + ", customerSince=" + customerSince + ", accounts="
				+ Arrays.toString(accounts) + "]";
	}

	public static UserProfileDTO deserialize(byte[] bytes) {
        LOGGER.info("Deserialization started.");
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
        	UserProfileDTO obj = (UserProfileDTO) in.readObject();
            LOGGER.info("Deserialization successful.");
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warning("Deserialization failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
	
}