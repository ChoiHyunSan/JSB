package com.example.article_site.service;

import com.example.article_site.domain.Author;
import com.example.article_site.exception.DataNotFoundException;
import com.example.article_site.form.ModifyPasswordForm;
import com.example.article_site.form.SignupForm;
import com.example.article_site.repository.AuthorRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.example.article_site.domain.Author.createAuthor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public Author create(SignupForm signUpForm){
        Author author = createAuthor(
                signUpForm.getUsername(),
                signUpForm.getEmail(),
                passwordEncoder.encode(signUpForm.getPassword1())
        );
        authorRepository.save(author);
        return author;
    }

    public Author findByUsername(String username){
        Optional<Author> byUsername = authorRepository.findByUsername(username);
        if(byUsername.isPresent()){
            return byUsername.get();
        }else{
            throw new DataNotFoundException("Author not found");
        }
    }

    public boolean modifyPassword(ModifyPasswordForm passwordModifyForm, String name) {
        Author author = findByUsername(name);
        if(!passwordEncoder.matches(passwordModifyForm.getOldPassword(), author.getPassword())
        || !passwordModifyForm.getNewPassword().equals(passwordModifyForm.getCheckPassword())){
            log.info("Before Encode {}", passwordModifyForm.getOldPassword());
            log.info("Old password {} Author.getPassword {}", passwordEncoder.encode(passwordModifyForm.getOldPassword()), author.getPassword());
            return false;
        }
        author.modifyPassword(passwordEncoder.encode(passwordModifyForm.getNewPassword()));
        authorRepository.save(author);
        return true;
    }

    public Optional<Author> checkUserPresent(String username, String email) {
        return authorRepository.findByUsernameAndEmail(username, email);
    }

    public String createNewPassword(Author author) {
        String newPassword = UUID.randomUUID().toString().substring(0, 10);
        author.modifyPassword(passwordEncoder.encode(newPassword));
        authorRepository.save(author);
        return newPassword;
    }
}
