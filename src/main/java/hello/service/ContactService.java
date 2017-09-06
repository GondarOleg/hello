package hello.service;

import hello.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import hello.repo.ContactRepository;

import java.util.List;

@Service
public class ContactService {

    private static final int PAGE_SIZE = 5;

    @Autowired
    ContactRepository contactRepository;

    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> getPage(int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");
        return contactRepository.findAll(request).getContent();
    }

    public void deleteAll() {
        contactRepository.deleteAll();
    }
}

