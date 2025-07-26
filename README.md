# QuizMaster

[Live Demo](https://quiz-production-8ae6.up.railway.app/)

---

## 🚀 Project Overview

QuizMaster is an interactive, responsive quiz platform that allows users to test their knowledge across various categories, earn badges, compete on leaderboards, and create custom quizzes. Built with modern web technologies, QuizMaster delivers an engaging learning experience on both desktop and mobile.

## 🌟 Features

* **Play Anywhere**: Fully responsive design works on mobile, tablet, and desktop.
* **Gamified Rewards**: Earn points, badges, and track daily streaks.
* **Global & Multiplayer**: Compete globally or challenge friends in real-time.
* **Personalized Analytics**: Detailed reports on performance and progress.
* **Custom Quiz Builder**: Create, share, and play your own quizzes.
* **Latest Articles & Tips**: Integrated blog section for study strategies.
* **Newsletter Signup**: Grow your learning community through emails.

## 🛠️ Tech Stack

* **Frontend**: HTML5, CSS3, Bootstrap 5, JavaScript (ES6+), AOS for animations
* **Backend**: Spring Boot, Thymeleaf
* **Database**: MySQL (or your choice of SQL)
* **Deployment**: Railway.app (CI/CD)

## 📂 Repository Structure

```
/ ── src/
    ├── main/
    │   ├── java/       # Spring Boot backend code
    │   └── resources/  # Thymeleaf templates & static assets
    └── test/          # Unit & integration tests
/ .gitignore
/ README.md
/ pom.xml
```

## ⚙️ Installation & Setup

1. **Clone the repo**

   ```bash
   git clone https://github.com/<your-username>/QuizMaster.git
   cd QuizMaster
   ```
2. **Configure database** in `application.properties`

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/quizmaster
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. **Build & run**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. **Access**
   Open [http://localhost:8080](http://localhost:8080) in your browser.

## 📈 Live Deployment

Check out the live version at:

👉 [https://quiz-production-8ae6.up.railway.app/](https://quiz-production-8ae6.up.railway.app/)

## 🤝 Contributing

Contributions are welcome! Please read our [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## 📜 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

## 📬 Contact

* **Author**: Sanjot Kaur
* *Mail* *: sanjotkaur6284@gmail.com
