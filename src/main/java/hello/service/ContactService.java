package hello.service;

import hello.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hello.repo.ContactRepository;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    public void deleteAll() {
        contactRepository.deleteAll();
    }
}

