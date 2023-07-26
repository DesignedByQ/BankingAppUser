package com.techprj.banking.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//import org.springframework.data.relational.core.mapping.Table;

//import com.techprj.accounts.entity.Account;
import com.techprj.banking.dto.AddressDTO;
import com.techprj.banking.dto.AuthUserDTO;
import com.techprj.banking.dto.UserProfileDTO;

@Entity
@Table(name="users")
public class UserProfile implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(UserProfile.class.getName());
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUserProfile;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Auth_User")
	private AuthUser authUser;
	private String firstName;
	private String middleName;
	private String lastName;
	@Column(unique = true)
	private Long mobile;
	@Column(unique = true)
	private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_ID")
	private Address address;
	private LocalDate customerSince;
	private Object[] accounts;
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(
//			name="account_user_profile", 
//			joinColumns = @JoinColumn(name = "user_profile_id"),
//			inverseJoinColumns = @JoinColumn(name = "account_id")
//	)
//	private List<Account> account = new ArrayList<>();
	
	public UserProfile() {
		super();
	}

	public UserProfile(Long idUserProfile, AuthUser authUser, String firstName, String middleName, String lastName,
		Long mobile, String email, Address address, LocalDate customerSince, Object[] accounts) {
		super();
		this.idUserProfile = idUserProfile;
		this.authUser = authUser;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.customerSince = customerSince;
		this.accounts = accounts;
	}



	public Long getIdUserProfile() {
		return idUserProfile;
	}

	public void setIdUserProfile(Long idUserProfile) {
		this.idUserProfile = idUserProfile;
	}

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
		return "UserProfile [idUserProfile=" + idUserProfile + ", firstName=" + firstName + ", authUser=" + authUser
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", mobile=" + mobile + ", email=" + email
				+ ", address=" + address + ", customerSince=" + customerSince + ", accounts="
				+ Arrays.toString(accounts) + "]";
	}
	
    public static UserProfile deserialize(byte[] bytes) {
        LOGGER.info("Deserialization started.");
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
        	UserProfile obj = (UserProfile) in.readObject();
            LOGGER.info("Deserialization successful.");
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warning("Deserialization failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
	

}
