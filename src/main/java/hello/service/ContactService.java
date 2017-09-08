package hello.service;

import hello.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hello.repo.ContactRepository;

import java.util.LinkedList;
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

    public List<Contact> findContactsByRegex(String regexForSearch) {
        List<Contact> contacts = new LinkedList<Contact>();
        for (Contact contact : findAllContacts()) {
            if (contact.getName().matches(regexForSearch) && !contact.getName().equals(regexForSearch)) {
                contacts.add(contact);
            }
        }
        return contacts;
    }

    public void deleteAll() {
        contactRepository.deleteAll();
    }
}

