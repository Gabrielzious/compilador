package br.sacredpunch.analisadores;

import br.sacredpunch.units.Token;
import br.sacredpunch.units.TokenType;

public class ExcecaoSintatica extends Exception {
	
	private TokenType token;
	
	
	public ExcecaoSintatica(TokenType tokenType) {
		this.token = tokenType;
	}

	@Override
	public String toString() {
		
		return "H� um erro Sint�tico neste c�digo: " + " >> " + this.token.toString();
	}

}
