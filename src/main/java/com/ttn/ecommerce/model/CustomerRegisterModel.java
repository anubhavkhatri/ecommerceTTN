package com.ttn.ecommerce.model;

import com.sun.istack.NotNull;
import com.ttn.ecommerce.domain.Address;
import com.ttn.ecommerce.validations.Email;
import com.ttn.ecommerce.validations.PasswordValid;
import com.ttn.ecommerce.validations.PasswordValueMatch;
import com.ttn.ecommerce.validations.Phone;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
public class CustomerRegisterModel {

    @NotBlank(message = "username is mandatory")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    private Set<Address> addresses;

    @Phone
    private String contact;

    @PasswordValid
    @NotBlank(message = "New password is mandatory")
    private String password;

    @PasswordValid
    @NotBlank(message = "Confirm Password is mandatory")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
