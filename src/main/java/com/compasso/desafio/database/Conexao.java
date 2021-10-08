package com.compasso.desafio.database;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.compasso.desafio.model.Produto;

public interface Conexao extends CrudRepository<Produto, String> {
		
	@Query("SELECT p FROM Produto p WHERE (p.name LIKE %?1%"
            + " OR p.description LIKE %?1%)"
            + " AND (price >= ?2 OR ?2 = 0)"
            + " AND (price <= ?3 OR ?3 = 0) ")
    public List<Produto> buscarProdutos(String q, double min_price, double max_price);
}
