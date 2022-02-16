package fr.epita.ratingmovies.service.mapper;


import fr.epita.ratingmovies.domain.*;
import fr.epita.ratingmovies.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieUserMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "movieUser.id", target = "movieUserId")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "movieUserId", target = "movieUser")
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "removeAddress", ignore = true)
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
