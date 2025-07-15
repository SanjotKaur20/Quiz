package com.interview.quiz.Controller;

import com.interview.quiz.Entity.Languages;

import com.interview.quiz.Entity.Question;
import com.interview.quiz.Entity.Quiz;
import com.interview.quiz.Entity.QuizHistory;
import com.interview.quiz.Entity.User;
import com.interview.quiz.Repository.LanguagesRepository;
import com.interview.quiz.Repository.QuestionRepository;
import com.interview.quiz.Repository.QuizHistoryRepository;
import com.interview.quiz.Repository.QuizRepository;
import com.interview.quiz.Repository.UserRepository;
import com.interview.quiz.Service.LanguageService;
import com.interview.quiz.Service.QuestionService;
import com.interview.quiz.Service.QuizService;
import com.interview.quiz.Service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("")
public class AdminQuizController {

    @Autowired
    private LanguageService languageService;
    @Autowired
    private QuestionService questionService; // Still works
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private LanguagesRepository languageRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private QuizHistoryRepository quizHistoryRepository;
    @Autowired
    private UserRepository userRepository;




    

    @Autowired
    private QuizService quizService;
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
    	System.out.println("GOT THE REQUEST00");
        model.addAttribute("language", new Languages());
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("languages", languageService.getAllLanguages()); // for quiz dropdown
        List<Quiz> quizzes = quizService.getAllQuizzes(); // or quizRepository.findAll()
        model.addAttribute("quizzes", quizzes);

        return "dashboard";
    }


    @GetMapping("/admin/add-language")
    public String showAddLanguageForm(Model model) {
        model.addAttribute("language", new Languages());
        return "add-language";
    }

    @PostMapping("/admin/add-language")
    public String addLanguage(@ModelAttribute Languages language) {
        languageService.addLanguage(language.getName());
        return "redirect:/admin/add-language?success";
    }

    @GetMapping("/admin/add-quiz")
    public String showAddQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("languages", languageService.getAllLanguages());
        return "add-quiz";
    }

    @PostMapping("/admin/add-quiz")
    public String addQuiz(@ModelAttribute Quiz quiz, @RequestParam Long languageId) {
        quizService.addQuiz(quiz.getName(), quiz.getLevel(), languageId);
        return "redirect:/admin/add-quiz?success";
    }
    @GetMapping("/admin/add-question")
    public String showAddQuestionForm(@RequestParam("quizId") Long quizId, Model model) {
        // Get the quiz by ID
        Quiz quiz = quizService.findById(quizId); // Make sure this returns a Quiz or throws an exception

        // Initialize a new Question and set its quiz
        Question form = new Question();
        form.setQuiz(quiz);

        // Add attributes to the model
        model.addAttribute("questionForm", form); // For binding form fields
        model.addAttribute("quiz", quiz);         // Optional: If you want to show quiz info like name
        // model.addAttribute("quizzes", quizService.getAllQuizzes()); // Optional: remove if not needed

        return "add-question"; // Thymeleaf template name
    }


    @PostMapping("/admin/add-question")
    public String saveQuestion(@ModelAttribute("questionForm") Question questionForm,
                               @RequestParam("quiz.id") Long quizId) {
        // Fetch full Quiz entity
        Quiz realQuiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID: " + quizId));

        // Manually set quiz into the question
        questionForm.setQuiz(realQuiz);

        // Save question
        questionService.saveQuestion(questionForm);

        return "redirect:/admin/view-questions?quizId=" + quizId;
    }

    @GetMapping("/admin/view-questions")
    public String viewQuestions(@RequestParam("quizId") Long quizId, Model model) {
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        Quiz quiz = quizService.findById(quizId);

        model.addAttribute("questions", questions);
        model.addAttribute("quizName", quiz.getName());

        return "view-questions";
    }

    @GetMapping("/admin/edit-quiz")
    public String showEditQuizForm(@RequestParam("quizId") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<Languages> languages = languageRepository.findAll();

        model.addAttribute("quiz", quiz);
        model.addAttribute("languages", languages);
        return "edit-quiz"; // Thymeleaf template name
    }

    @PostMapping("/admin/update-quiz")
    public String updateQuiz(@ModelAttribute("quiz") Quiz updatedQuiz) {
        Quiz quiz = quizRepository.findById(updatedQuiz.getId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quiz.setName(updatedQuiz.getName());
        quiz.setLevel(updatedQuiz.getLevel());
        quiz.setLanguage(updatedQuiz.getLanguage());

        quizRepository.save(quiz);

        return "redirect:/admin/dashboard";
    }
    @GetMapping("/admin/delete-quiz")
    public String deleteQuiz(@RequestParam("quizId") Long quizId) {
        quizRepository.deleteById(quizId);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/admin/edit-question")
    public String editQuestionForm(@RequestParam("questionId") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + questionId));
        model.addAttribute("questionForm", question);
        model.addAttribute("quiz", question.getQuiz());
        return "edit-question";
    }

    // ✅ Update Question
    @PostMapping("/admin/update-question")
    public String updateQuestion(@ModelAttribute("questionForm") Question questionForm) {
        Long quizId = questionForm.getQuiz().getId();
        questionRepository.save(questionForm); // ID present = update
        return "redirect:/admin/view-questions?quizId=" + quizId;
    }

    // ✅ Delete Question
    @GetMapping("/admin/delete-question")
    public String deleteQuestion(@RequestParam("questionId") Long questionId,
                                 @RequestParam("quizId") Long quizId) {
        questionRepository.deleteById(questionId);
        return "redirect:/admin/view-questions?quizId=" + quizId;
    }
//    @GetMapping("/admin/users")
//    public String viewAllUsers(Model model) {
//        List<User> users = userService.findAllUsers(); // Add this in UserService
//        model.addAttribute("users", users);
//        return "admin-user-list"; // new Thymeleaf template
//    }
//    @GetMapping("/admin/user-history")
//    public String viewUserHistory(@RequestParam("userId") Long userId, Model model) {
//        User user = userService.findUserById(userId); // Add in UserService
//        List<QuizHistory> history = quizHistoryRepository.findByUserId(userId);
//        model.addAttribute("user", user);
//        model.addAttribute("history", history);
//        return "admin-user-history"; // new Thymeleaf template
//    }
//
    @GetMapping("/admin/users")
    public String viewAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin-user-list"; // You’ll create this Thymeleaf template
    }

    // ✅ View selected user's quiz history
    @GetMapping("/admin/user-history")
    public String viewUserHistory(@RequestParam("userId") Integer userId, Model model) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/admin/users?error=UserNotFound";
        }

        List<QuizHistory> history = quizHistoryRepository.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("history", history);
        return "admin-user-history"; // You’ll create this Thymeleaf template
    }
}










