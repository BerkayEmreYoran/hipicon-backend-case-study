package com.hipicon.casestudy.product.repository;

import com.beyt.jdq.jpa.repository.JpaDynamicQueryRepository;
import com.hipicon.casestudy.product.dto.PendingProductCountByDesigner;
import com.hipicon.casestudy.product.entity.Product;

import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaDynamicQueryRepository<Product, Long> {

    @Query("""
        select 
            p.sellerName as designer,
            count(p.id) as count
        from Product p
        where p.status = 'PENDING'
        group by p.sellerName
    """)
    List<PendingProductCountByDesigner> countPendingProductsGroupedByDesigner();

}