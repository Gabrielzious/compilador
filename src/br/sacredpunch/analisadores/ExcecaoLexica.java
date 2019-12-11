package br.sacredpunch.analisadores;

import br.sacredpunch.units.Token;
import br.sacredpunch.units.TokenType;

public class ExcecaoLexica extends Exception {
	
	// Criar metodos para testar os lexemas
	
	private char c;
	private String lexema;
	
	
	public ExcecaoLexica(char c2, String string) {
		this.c = c2;
		this.lexema = string;
	}

	@Override
	public String toString() {
		
		return this.c + ": " + "N�o � um lexema valido" + " " + "--> " + "|" + this.lexema;
	}
	
	

}
