package fi.haagahelia.bookstore.web;

/*import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/booklist")
    public String showBookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        repository.save(book);
        return "redirect:/booklist";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/booklist";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Book book = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "editbook";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book) {
        book.setId(id);
        repository.save(book);
        return "redirect:/booklist";
    }
}*/

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    // Display the list of books
    @GetMapping("/booklist")
    public String showBookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    // Display the add book form
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }

    // Save a new book
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    // Delete a book (only accessible by ADMIN users)
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    // Display the edit book form
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }

    // Update an existing book
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book) {
        book.setId(id);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    // Serve the custom login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
