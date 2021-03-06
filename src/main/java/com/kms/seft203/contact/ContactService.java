package com.kms.seft203.contact;

import com.kms.seft203.exceptions.DataNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    final
    ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    public Contact findById(Long id) {
        var contact = contactRepository.findById(id).orElse(null);

        if (ObjectUtils.isEmpty(contact)) {
            throw new DataNotFoundException();
        }

        return contact;
    }

    public Contact create(SaveContactRequest saveContactRequest) {
        return contactRepository.save(Contact.of(saveContactRequest));
    }

    public Contact update(Long id, SaveContactRequest saveContactRequest) {
        if (!contactRepository.existsById(id)) {
            throw new DataNotFoundException();
        } else {
            return contactRepository.save(Contact.of(saveContactRequest));
        }
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
