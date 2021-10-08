package com.compasso.desafio.service;

import java.util.List;

import com.compasso.desafio.database.Conexao;
import com.compasso.desafio.exception.ParametrosInvalidosException;
import com.compasso.desafio.exception.ProdutoNaoEncontradoException;
import com.compasso.desafio.model.Produto;

public class ProdutoService {
	
	private Conexao conexao;
	
	public ProdutoService(Conexao conn){
		this.conexao = conn;
	}
	
	public Produto adicionarProduto(Produto produto) 
			throws ParametrosInvalidosException{	
		this.validarParamentrosEntrada(produto);
		conexao.save(produto);
		return produto;
	}

	public Produto atualizarProduto(Produto produtoAtualizado, String id) 
			throws ParametrosInvalidosException, ProdutoNaoEncontradoException{
		this.validarParamentrosEntrada(produtoAtualizado);
		
		Produto produto = conexao.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException());
		
		produto.setName(produtoAtualizado.getName());
		produto.setDescription(produtoAtualizado.getDescription());
		produto.setPrice(produtoAtualizado.getPrice());
    	  
        conexao.save(produto);
        
        return produto;
	}
	
	public List<Produto> listarProdutos() {
		return (List<Produto>) conexao.findAll();
	}
	
	public Produto buscarProduto(String id) throws ProdutoNaoEncontradoException {		
		Produto produto = conexao.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException());
		return produto; 		
	}
		
	public List<Produto> buscarProdutoFiltro(String q, double min_price, double max_price){	
		List<Produto> listaProdutos = (List<Produto>) conexao.buscarProdutos(q, min_price, max_price);
		return listaProdutos;
	}
	
	public void deletarProduto(String id) throws ProdutoNaoEncontradoException{	
		Produto produto = conexao.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException());	    	  
        conexao.delete(produto);
	}
	
	private void validarParamentrosEntrada(Produto produto)
			throws ParametrosInvalidosException {
			if (produto.getName() == null || produto.getName().equals(""))
				throw new ParametrosInvalidosException("name");
			
			if (produto.getDescription() == null || produto.getDescription().equals(""))
				throw new ParametrosInvalidosException("description");
			
			if (produto.getPrice() <= 0)
				throw new ParametrosInvalidosException("price");
	}
}
