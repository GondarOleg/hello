package hello.service;

import hello.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hello.repo.ContactRepository;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    private List<Contact> allContactsList;

    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> findContactsByRegex(Pattern pattern) {
        List<Contact> contacts = new LinkedList<Contact>();
        if (CollectionUtils.isEmpty(allContactsList)) {
            allContactsList = contactRepository.findAll();
        }
        for (Contact contact : allContactsList) {
            Matcher matcher = pattern.matcher(contact.getName());
            System.out.println(pattern.toString());
            if (matcher.matches() && !contact.getName().equals(pattern.toString())) {
                contacts.add(contact);
            }
        }
        return contacts;
    }

    public void deleteAll() {
        contactRepository.deleteAll();
    }
}

