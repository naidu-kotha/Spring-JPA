package com.example.goodreads.service;

import java.util.ArrayList;
import java.util.List;

import com.example.goodreads.model.Book;
import com.example.goodreads.repository.BookJpaRepository;
import com.example.goodreads.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookJpaService implements BookRepository {

	@Autowired
	private BookJpaRepository bookJpaRepository;

	@Override
	public ArrayList<Book> getBooks() {
		List<Book> booksList = bookJpaRepository.findAll();
		ArrayList<Book> books = new ArrayList<>(booksList);
		return books;
	}

	@Override
	public Book getBookById(int bookId) {
		try {
			Book book = bookJpaRepository.findById(bookId).get();
			return book;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Book addBook(Book book) {
		bookJpaRepository.save(book);
		return book;
	}

	@Override
	public Book updateBook(int bookId, Book book) {
		try {
			Book existingBook = bookJpaRepository.findById(bookId).get();
			if (book.getName() != null) {
				existingBook.setName(book.getName());
			}
			if (book.getImageUrl() != null) {
				existingBook.setImageUrl(book.getImageUrl());
			}
			bookJpaRepository.save(existingBook);
			return existingBook;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteBook(int bookId) {
		try {
			bookJpaRepository.deleteById(bookId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}