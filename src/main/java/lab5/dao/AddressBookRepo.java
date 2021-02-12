package lab5.dao;

import lab5.model.AddressBook;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="dbAddrBooks", path="dbAddrBooks")
public interface AddressBookRepo extends PagingAndSortingRepository<AddressBook, Long> {
    AddressBook findById(long id);
}
