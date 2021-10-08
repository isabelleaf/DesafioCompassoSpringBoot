package com.compasso.desafio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.desafio.database.Conexao;
import com.compasso.desafio.exception.ParametrosInvalidosException;
import com.compasso.desafio.exception.ProdutoNaoEncontradoException;
import com.compasso.desafio.model.ErroResponse;
import com.compasso.desafio.model.Produto;
import com.compasso.desafio.service.ProdutoService;

@RestController
@RequestMapping("/products")
public class ProdutoController {
		
	private ProdutoService produtoService;
	
	ProdutoController(Conexao conn){
		produtoService = new ProdutoService(conn);
	}
	
	@PostMapping	
	@ResponseBody
	public ResponseEntity<Object> adicionarProduto(@RequestBody Produto produtoNovo){	
		
		try {
			Produto produto = produtoService.adicionarProduto(produtoNovo);
			
			return new ResponseEntity<Object>(produto, HttpStatus.CREATED); 
			
		} catch(ParametrosInvalidosException e) {
			return new ResponseEntity<Object>(new ErroResponse(400, "Campos faltantes ou inválidos: " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
		
	@PutMapping(value = "/{id}")	
	public ResponseEntity<Object>atualizarProduto(@RequestBody Produto produtoAtualizado, @PathVariable(value = "id") String id){	
		try {
			
			Produto produto = produtoService.atualizarProduto(produtoAtualizado, id);	        
			return new ResponseEntity<Object>(produto, HttpStatus.OK); 
			
		} catch(ParametrosInvalidosException e) {
			return new ResponseEntity<Object>(new ErroResponse(400, "Campos faltantes ou inválidos: " + e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch(ProdutoNaoEncontradoException e) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping
	public List<Produto> listarProdutos() {
		return produtoService.listarProdutos();
	}
	
	
	@GetMapping(value = "/{id}")	
	public ResponseEntity<Object> buscarProduto(@PathVariable(value = "id") String id) {
		try {
			Produto produto = produtoService.buscarProduto(id);
			return new ResponseEntity<Object>(produto, HttpStatus.OK); 
			
		} catch(ProdutoNaoEncontradoException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/search")	
	public ResponseEntity<Object> buscarProdutoFiltro(@RequestParam(required=false, defaultValue = "") String q, @RequestParam(required=false, defaultValue = "0") double min_price, @RequestParam(required=false, defaultValue = "0") double max_price){	
		List<Produto> listaProdutos = produtoService.buscarProdutoFiltro(q, min_price, max_price);		
		return new ResponseEntity<Object>(listaProdutos, HttpStatus.OK); 
	}
	
	@DeleteMapping(value = "/{id}")	
	public ResponseEntity<Object> deletarProduto(@RequestBody Produto produtoAtualizado, @PathVariable(value = "id") String id){	
		try {
			produtoService.deletarProduto(id);	        
			return new ResponseEntity<Object>(HttpStatus.OK); 
			
		} catch(ProdutoNaoEncontradoException e) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
	
}
