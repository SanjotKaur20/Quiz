package com.interview.quiz.Controller;



import com.interview.quiz.Entity.Languages;

import com.interview.quiz.Entity.Question;
import com.interview.quiz.Entity.Quiz;
import com.interview.quiz.Entity.QuizHistory;
import com.interview.quiz.Entity.User;
import com.interview.quiz.Repository.LanguagesRepository;
import com.interview.quiz.Repository.QuizHistoryRepository;
import com.interview.quiz.Repository.QuizRepository;
import com.interview.quiz.Repository.UserRepository;
import com.interview.quiz.Service.EmailService;
import com.interview.quiz.Service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LanguagesRepository languagesRepository;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizHistoryRepository quizHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
@GetMapping("/")
public String Home(Model model) {
	return "Home";
}

    // Show Sign Up form
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());  // "user" must match th:object
        return "signup";
    }

    // Handle sign-up form submission
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user, Model model) {
        try {
            userService.addNew(user);
            return "redirect:/login?signupSuccess"; // assuming /login exists
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String token = UUID.randomUUID().toString();
            User user = userOptional.get();
            user.setResetToken(token);
            userRepository.save(user);

            String resetLink = "http://localhost:8080/reset-password?token=" + token;
            emailService.sendSimpleMessage(email, "Password Reset Request",
                    "Hi " + user.getName() + ",\n\nTo reset your password, click the link below:\n" + resetLink);

            model.addAttribute("success", "Reset link sent to your email.");
        } else {
            model.addAttribute("error", "No user found with that email.");
        }
        return "forgot-password";
    }
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String handlePasswordReset(@RequestParam String token,
                                      @RequestParam String newPassword,
                                      @RequestParam String confirmPassword,
                                      Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("token", token);
            return "reset-password";
        }

        Optional<User> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Invalid or expired token.");
            return "reset-password";
        }

        User user = userOptional.get();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user.setResetToken(null); // Clear token after reset
        userRepository.save(user);

        model.addAttribute("success", "Password has been reset successfully!");
        return "reset-password";
    }


    @GetMapping("/user/change-password")
    public String showChangePasswordForm(Model model,Principal principal) {
        String email = principal.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=user_not_found";
        }

        User user = optionalUser.get();
        model.addAttribute("user", user);


        return "change-password";
    }

    @PostMapping("/user/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model,
                                 Principal principal) {

        String email = principal.getName();

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match.");
            return "change-password";
        }

        boolean success = userService.changeUserPassword(email, oldPassword, newPassword);
        if (success) {
            model.addAttribute("success", "Password changed successfully.");
        } else {
            model.addAttribute("error", "Old password is incorrect.");
        }

        return "change-password";
    }


//    @GetMapping("/user/user-dashboard")
//    public String showLanguages(Model model,Principal principal) {
//        String email = principal.getName(); // If you use email as the username
//
//        // Get user from DB using email
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            model.addAttribute("user", user);
//        } else {
//            // handle error, redirect or show message
//            return "redirect:/login?error=user_not_found";
//        }
//
//        List<Languages> languages = languagesRepository.findAll();
//        model.addAttribute("user", user);
//        model.addAttribute("languages", languages);
//        return "user-dashboard"; // Template name
//    }
//
    @GetMapping("/user/user-dashboard")
    public String showLanguages(Model model, Principal principal) {
        String email = principal.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=user_not_found";
        }

        User user = optionalUser.get();
        List<Languages> languages = languagesRepository.findAll();

        model.addAttribute("languages", languages);
        model.addAttribute("user", user);

        return "user-dashboard";
    }

    @GetMapping("/user/quizzes")
    public String showQuizzesByLanguage(@RequestParam("languageId")
    Long languageId, Model model,Principal principal) {
        String email = principal.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=user_not_found";
        }

        User user = optionalUser.get();

    	List<Quiz> quizzes = quizRepository.findByLanguageId(languageId);
        Languages language = languagesRepository.findById(languageId).orElse(null);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("language", language);
        model.addAttribute("user", user);

        return "quizzes-by-language"; // Template name
    }
    @GetMapping("/user/start-test")
    public String startTest(@RequestParam("quizId") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<Question> questions = quiz.getQuestions(); // Or fetch via service

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        return "start-test";  // Thymeleaf template
    }
//    @PostMapping("/user/submit-test")
//    public String submitTest(@RequestParam("quizId") Long quizId,
//                             @RequestParam Map<String, String> allParams,
//                             Model model,
//                             Principal principal) {
//        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
//        List<Question> questions = quiz.getQuestions();
//
//        int total = questions.size();
//        int correct = 0;
//
//        for (Question q : questions) {
//            String submittedAnswer = allParams.get("answers[" + q.getId() + "]");
//            if (q.getAnswer().equalsIgnoreCase(submittedAnswer)) {
//                correct++;
//            }
//        }
//        User user = userService.getUserByEmail(principal.getName());
//
//        // Save quiz history
//        QuizHistory history = new QuizHistory();
//        history.setQuiz(quiz);
//        history.setUser(user);
//        history.setScore(correct);
//        history.setTotalQuestions(total);
//        history.setSubmittedAt(LocalDateTime.now());
//        quizHistoryRepository.save(history);
//
//
//        model.addAttribute("score", correct);
//        model.addAttribute("total", total);
//        return "test-result";  // new template to display result
//    }
    @PostMapping("/user/submit-test")
    public String submitTest(@RequestParam("quizId") Long quizId,
                             @RequestParam Map<String, String> allParams,
                             Model model,
                             Principal principal) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<Question> questions = quiz.getQuestions();

        int total = questions.size();
        int correct = 0;

        for (Question q : questions) {
            String submittedAnswer = allParams.get("answers[" + q.getId() + "]");
            if (q.getAnswer().equalsIgnoreCase(submittedAnswer)) {
                correct++;
            }
        }

        // ✅ Get user directly from repository
        User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

        // Save quiz history
        QuizHistory history = new QuizHistory();
        history.setQuiz(quiz);
        history.setUser(user);
        history.setScore(correct);
        history.setStartTime(LocalDateTime.now());
        history.setEndTime(LocalDateTime.now());

        quizHistoryRepository.save(history);

        model.addAttribute("score", correct);
        model.addAttribute("total", total);
        return "test-result";
    }
    @GetMapping("/user/history")
    public String showQuizHistory(Model model, Principal principal) {
        String email = principal.getName();

        // Get user by email
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=user_not_found";
        }
        User user = optionalUser.get();

        // Get history records
        List<QuizHistory> historyList = quizHistoryRepository.findByUser(user);
        model.addAttribute("historyList", historyList);
        model.addAttribute("user", user);


        return "user-quiz-history"; // Create this Thymeleaf template
    }


}
