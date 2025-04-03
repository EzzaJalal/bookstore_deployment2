package fi.haagahelia.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;
import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(BookRepository bookRepository,
			CategoryRepository categoryRepository,
			AppUserRepository userRepository,
			BCryptPasswordEncoder encoder) {
		return (args) -> {
			log.info("Saving sample books and categories...");

			// Create and save sample categories
			Category fiction = new Category("Fiction");
			Category nonFiction = new Category("Non-Fiction");
			Category science = new Category("Science");

			categoryRepository.save(fiction);
			categoryRepository.save(nonFiction);
			categoryRepository.save(science);

			// Create and save sample books
			bookRepository.save(new Book("The Alchemist", "Paulo Coelho", 1988, "978-0061122415", 10.99, fiction));
			bookRepository.save(new Book("1984", "George Orwell", 1949, "978-0451524935", 8.99, nonFiction));
			bookRepository
					.save(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "978-0061120084", 12.50, science));

			// Create sample users with BCrypt-hashed passwords
			AppUser user = new AppUser("user", encoder.encode("userpassword"), "user@example.com", "ROLE_USER");
			AppUser admin = new AppUser("admin", encoder.encode("adminpassword"), "admin@example.com", "ROLE_ADMIN");
			userRepository.save(user);
			userRepository.save(admin);

			// Log the login credentials for testing purposes
			log.info("User Login Info:");
			log.info("User - Username: user, Password: userpassword");
			log.info("Admin - Username: admin, Password: adminpassword");

			// Print all books for verification
			log.info("Fetching all books:");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}
		};
	}
}

/*
 * import org.springframework.boot.SpringApplication;
 * import org.springframework.boot.autoconfigure.SpringBootApplication;
 * 
 * @SpringBootApplication
 * public class BookstoreApplication {
 * public static void main(String[] args) {
 * SpringApplication.run(BookstoreApplication.class, args);
 * }
 * }
 */
