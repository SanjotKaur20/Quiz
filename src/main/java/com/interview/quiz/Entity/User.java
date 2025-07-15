package com.interview.quiz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name ="password",nullable = false)
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    
    @Column(name = "phone", length = 15)
    private String phoneNumber;
    
    @Column
    private String gender;
    @Column(name = "reset_token")
    private String resetToken;


    public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	@Column(name="photo")
    private String photo;
    public User() {}
	public User(Long id, String name, String email, String password, String phoneNumber,
			String gender, String photo) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.photo = photo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", usertypeId="
				+ userType + ", phoneNumber=" + phoneNumber + ", gender=" + gender + ", photo=" + photo + "]";
	}
	public void setUserType(UserType userType) {
	    this.userType = userType;
	}
	public String getEmail() {
	    return this.email;
	}
	public String getPassword() {
	    return password;
	}

	public void setPassword(String password) {
	    this.password = password;
	}



}