package com.ecommerce.repository;

import com.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    @Query("select c.id from Cart c where c.status = 'READY' order by c.creationDate asc")
    List<Long> findIdByStatusReadyOrderByCreationDateAsc();
}
