package br.sacredpunch.compilador;

import java.io.FileNotFoundException;
import java.io.IOException;

import br.sacredpunch.analisadores.Sintatico;
import br.sacredpunch.units.ErrorHandler;
import br.sacredpunch.analisadores.ExcecaoLexica;
import br.sacredpunch.analisadores.ExcecaoSintatica;
import br.sacredpunch.analisadores.Lexico;

public class Compilador {

	public static void main(String[] args) throws ExcecaoLexica, IOException {
		if (args.length != 1) {
			System.out.println("Arquivo invalido");
		}
		else {			
			Sintatico s = new Sintatico(args[0]);
			try {			
				s.processar();
			}catch(FileNotFoundException fnf) {
				System.out.println("Arquivo não encontrado.");
			}catch(ExcecaoSintatica sin) {
				
				ErrorHandler.getInstance().printErrorSintatic(sin);
			}

		}

	}
}