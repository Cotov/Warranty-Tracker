package bg.softuni.warranty_tracker.mapper.contact;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.contact.ContactDto;
import bg.softuni.warranty_tracker.model.entity.contact.Contact;

@Component
public class ContactMapper {

    public Contact toContact(ContactDto contactDto) {
        return contactDto == null ? null
                : Contact.builder()
                        .email(contactDto.getEmail())
                        .phone(contactDto.getPhone())
                        .website(contactDto.getWebsite())
                        .build();
    }

    public ContactDto toContactDto(Contact contact) {
        return contact == null ? null
                : ContactDto.builder()
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .website(contact.getWebsite())
                        .build();
    }

}
