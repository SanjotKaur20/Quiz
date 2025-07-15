package com.interview.quiz.Entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

	@Table(name = "languages")
	public class Languages {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

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

		public List<Quiz> getQuizzes() {
			return quizzes;
		}

		public void setQuizzes(List<Quiz> quizzes) {
			this.quizzes = quizzes;
		}
		@Column(nullable = false, unique = true)
	    private String name;
	    public Languages() {}

	    public Languages(String name) {
	        this.name = name;
	    }
	    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
	    private List<Quiz> quizzes;




}
