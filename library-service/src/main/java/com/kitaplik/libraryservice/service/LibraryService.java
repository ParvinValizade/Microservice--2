package com.kitaplik.libraryservice.service;

import com.kitaplik.libraryservice.client.BookServiceClient;
import com.kitaplik.libraryservice.dto.AddBookRequest;
import com.kitaplik.libraryservice.dto.LibraryDto;
import com.kitaplik.libraryservice.exception.LibraryNotFoundException;
import com.kitaplik.libraryservice.model.Library;
import com.kitaplik.libraryservice.repository.LibraryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository repository;
    private final BookServiceClient bookServiceClient;

    public LibraryService(LibraryRepository repository, BookServiceClient bookServiceClient) {
        this.repository = repository;
        this.bookServiceClient = bookServiceClient;
    }

    public LibraryDto getAllBooksInLibraryById(String id){
        Library library = repository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: "+ id));
        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(bookServiceClient::getBookById)
                        .map(ResponseEntity::getBody)
                        .collect(Collectors.toList()));
        return libraryDto;
    }

    public LibraryDto createLibrary(){
        Library library = repository.save(new Library());
        return new LibraryDto(library.getId());
    }

    public void addBookToLibrary(AddBookRequest request){
        String bookId = bookServiceClient.getBookByIsbn(request.getIsbn()).getBody().getBookId();

        Library library = repository.findById(request.getId())
                .orElseThrow(()->new LibraryNotFoundException("Library could not found by id: "+ request.getId()));
   library.getUserBook()
           .add(bookId);

   repository.save(library);
    }
}
