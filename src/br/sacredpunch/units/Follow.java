package br.sacredpunch.units;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.sacredpunch.units.TokenType;

public class Follow {

	private static Follow instance = new Follow();

	public ArrayList<String> token;

	private TokenType tokenType;

	private String[] strCarga = new String[this.token.size()];

	public Follow() {

	}

	private void putToken(Producoes rule) {
		if (rule == Producoes.BLOCO) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.CMDS) {
			this.token.add(TokenType.END.toString());

		} else if (rule == Producoes.CMD) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.DECL) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.COND) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.CNDB) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.ATRIB) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.EXP) {
			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.FID) {

			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.FOPNUM) {
			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.FEXPNUM_1) {
			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.FNUM) {
			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.FLPAR) {
			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.FEXPNUM) {
			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.FRPAR) {
			this.token.add(TokenType.TERM.toString());

		} else if (rule == Producoes.EXPLO) {
			this.token.add(TokenType.TERM.toString());
			this.token.add(TokenType.R_PAR.toString());

		} else if (rule == Producoes.FID_1) {
			this.token.add(TokenType.TERM.toString());
			this.token.add(TokenType.R_PAR.toString());

		} else if (rule == Producoes.FVALLOG) {
			this.token.add(TokenType.TERM.toString());
			this.token.add(TokenType.R_PAR.toString());

		} else if (rule == Producoes.EXPNUM) {
			this.token.add(TokenType.RELOP.toString());
			this.token.add(TokenType.TO.toString());
			this.token.add(TokenType.BEGIN.toString());
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.TERM.toString());
			this.token.add(TokenType.R_PAR.toString());

		} else if (rule == Producoes.XEXPNUM) {
			this.token.add(TokenType.RELOP.toString());
			this.token.add(TokenType.TO.toString());
			this.token.add(TokenType.BEGIN.toString());
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.TERM.toString());
			this.token.add(TokenType.R_PAR.toString());

		} else if (rule == Producoes.OPNUM) {
			this.token.add(TokenType.L_PAR.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.NUM_INT.toString());
			this.token.add(TokenType.NUM_FLOAT.toString());

		} else if (rule == Producoes.VAL) {
			this.token.add(TokenType.ARIT_AS.toString());
			this.token.add(TokenType.ARIT_MD.toString());
			this.token.add(TokenType.RELOP.toString());
			this.token.add(TokenType.TO.toString());
			this.token.add(TokenType.BEGIN.toString());
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.TERM.toString());
			this.token.add(TokenType.R_PAR.toString());

		} else if (rule == Producoes.REP) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.REPF) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());

		} else if (rule == Producoes.REPW) {
			this.token.add(TokenType.DECLARE.toString());
			this.token.add(TokenType.IF.toString());
			this.token.add(TokenType.FOR.toString());
			this.token.add(TokenType.WHILE.toString());
			this.token.add(TokenType.ID.toString());
			this.token.add(TokenType.END.toString());
			this.token.add(TokenType.END_PROG.toString());
			this.token.add(TokenType.ELSE.toString());
		}

	}

	public static Follow getInstance() {
		return instance;
	}

	private void ordenaTokens() {
		for (int i = 0; i < this.token.size(); i++) {
			for (int j = 1; j < this.token.size(); j++) {

				if (this.token.get(i).length() < this.token.get(j).length()) {
					this.strCarga[i] = this.token.get(j);
					this.strCarga[j] = this.token.get(i);
				}
			}
		}
	}

	public Boolean isFollowOf(Producoes rule) {
		putToken(rule);
		ordenaTokens();
		buscaTokens();

		if (buscaTokens()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean buscaTokens() {

		int x = 0;
		int meio;
		int inicio = 0;
		int fim = this.strCarga.length;
		while (inicio <= fim) {
			meio = (inicio + fim) / 2;
			if (this.strCarga[x] == this.strCarga[meio]) {
				return true;
			} else if (this.strCarga[x].length() < this.strCarga[meio].length()) {
				fim = meio - 1;
			} else if (this.strCarga[x].length() > this.strCarga[meio].length()) {
				inicio = meio + 1;
			}

		}
		return false;
	}

}
