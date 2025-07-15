package com.interview.quiz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="userType")
public class UserType {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="user_type_id")
	    private Integer userTypeId;

	    @Column(name="user_type_name")
	    private String userTypeName;

		public Integer getUserTypeId() {
			return userTypeId;
		}

		public void setUserTypeId(Integer userTypeId) {
			this.userTypeId = userTypeId;
		}

		public String getUserTypeName() {
			return userTypeName;
		}

		public void setUserTypeName(String userTypeName) {
			this.userTypeName = userTypeName;
		}
	}

	


