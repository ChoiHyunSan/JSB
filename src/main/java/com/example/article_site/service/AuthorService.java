package com.example.article_site.service;

import com.example.article_site.domain.Author;
import com.example.article_site.exception.DataNotFoundException;
import com.example.article_site.form.SignupForm;
import com.example.article_site.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.article_site.domain.Author.createAuthor;

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
}
