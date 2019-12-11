package br.sacredpunch.analisadores;

import java.io.FileNotFoundException;
import java.io.IOException;

import br.sacredpunch.units.ErrorHandler;
import br.sacredpunch.units.Follow;
import br.sacredpunch.units.Producoes;
import br.sacredpunch.units.TabSimbolos;
import br.sacredpunch.units.Token;
import br.sacredpunch.units.TokenType;

public class Sintatico {
	private Lexico lex;

	private Token t;
	private Producoes bloco;

	Follow follow;

	public Sintatico(String nomeArquivo) throws FileNotFoundException {
		this.lex = new Lexico();
		this.lex.getFileName(nomeArquivo);
	}

	public void processar() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		while (t.getTokenType() != TokenType.EOF) {
			loadS(t);
			t.printToken();
			t = lex.nextToken();
		}

		TabSimbolos.getInstance().printTabela();

	}

	private void armazenaToken(Producoes bloco, Token token) {
		this.t = token;
		this.bloco = bloco;
	}

	private Token getStorageToken() {
		return this.t;
	}
	//começa a atribuição dos valores para a produção de "S"
	public void loadS(Token t) throws IOException, ExcecaoSintatica {
		if (t.getTokenType() == TokenType.PROGRAM) {
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.ID) {
				t = lex.nextToken();
				if (t.getTokenType() == TokenType.TERM) {
					loadBLOCO();
					t = lex.nextToken();
					if (t.getTokenType() == TokenType.END_PROG) {

						t = lex.nextToken();

						if (t.getTokenType() == TokenType.TERM) {
							armazenaToken(Producoes.S, t);
						
						} else {
							ErrorHandler.getInstance().printErrorSintDefault(t.getTokenType(), t.getLexema());
						}

					} else {
						ErrorHandler.getInstance().printErrorSintDefault(t.getTokenType(), t.getLexema());
					}

				} else {
					ErrorHandler.getInstance().printErrorSintDefault(t.getTokenType(), t.getLexema());
				}

			} else {
				ErrorHandler.getInstance().printErrorSintDefault(t.getTokenType(), t.getLexema());
			}

		} else {
			ErrorHandler.getInstance().printErrorSintDefault(t.getTokenType(), t.getLexema());
		}
	}

	public void loadBLOCO() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();

		if (t.getTokenType() == TokenType.BEGIN) {
			armazenaToken(Producoes.BLOCO, t);
			loadCMDS();
			t = lex.nextToken();
			if (t.getTokenType() != TokenType.END) {
				ErrorHandler.getInstance().printErrorSintDefault(t.getTokenType(), t.getLexema());
			}
		} else if ((t.getTokenType() == TokenType.ID) ||
				 (t.getTokenType() == TokenType.IF)   ||
				 (t.getTokenType() == TokenType.FOR)  ||
				 (t.getTokenType() == TokenType.WHILE)||
				 (t.getTokenType() == TokenType.DECLARE)) {
			armazenaToken(Producoes.BLOCO, t);
			loadCMD();
		} else {
			ErrorHandler.getInstance().printErrorSintDefault(t.getTokenType(), t.getLexema());

		}

	}

	public void loadCMDS() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.DECLARE) {
			armazenaToken(Producoes.CMDS, t);
			loadDECL();
			loadCMDS();
		} else if (t.getTokenType() == TokenType.IF) {
			armazenaToken(Producoes.CMDS, t);
			loadCOND();
			loadCMDS();
		} else if (t.getTokenType() == TokenType.FOR) {
			armazenaToken(Producoes.CMDS, t);
			loadREPF();
			loadCMDS();
		} else if (t.getTokenType() == TokenType.WHILE) {
			armazenaToken(Producoes.CMDS, t);
			loadREPW();
			loadCMDS();
		} else if (t.getTokenType() == TokenType.ID) {
			armazenaToken(Producoes.CMDS, t);
			loadATRIB();
			loadCMDS();
		} else if (follow.isFollowOf(Producoes.CMDS)) {
			armazenaToken(Producoes.CMDS, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}

	}

	public void loadDECL() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.DECLARE) {
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.ID) {
				t = lex.nextToken();
				if (t.getTokenType() == TokenType.TYPE) {
					t = lex.nextToken();
					if (t.getTokenType() == TokenType.TERM) {
						armazenaToken(Producoes.DECL, t);
					} else {
						throw new ExcecaoSintatica(t.getTokenType());
					}
				} else {
					throw new ExcecaoSintatica(t.getTokenType());
				}
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadCMD() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.DECLARE) {
			armazenaToken(Producoes.CMD, t);
			loadDECL();
		} else if (t.getTokenType() == TokenType.IF) {
			armazenaToken(Producoes.CMD, t);
			loadCOND();
		} else if ((t.getTokenType() == TokenType.FOR) || (t.getTokenType() == TokenType.WHILE)) {
			armazenaToken(Producoes.CMD, t);
			loadREP();
		} else if (t.getTokenType() == TokenType.ID) {
			armazenaToken(Producoes.CMD, t);
			loadATRIB();
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadCOND() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.IF) {
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.L_PAR) {
				armazenaToken(Producoes.COND, t);
				loadEXPLO();
				t = lex.nextToken();
				if (t.getTokenType() == TokenType.R_PAR) {
					t = lex.nextToken();
					if (t.getTokenType() == TokenType.THEN) {
						armazenaToken(Producoes.COND, t);
						loadBLOCO();
						loadCNDB();
					} else {
						throw new ExcecaoSintatica(t.getTokenType());
					}
				} else {
					throw new ExcecaoSintatica(t.getTokenType());
				}
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}

	}

	public void loadCNDB() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.ELSE) {
			loadBLOCO();
		} else if (follow.isFollowOf(Producoes.CNDB)) {
			armazenaToken(Producoes.CNDB, t);
			follow.token.clear();
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}

	}

	public void loadATRIB() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.ID) {
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.ASSIGN) {
				armazenaToken(Producoes.ATRIB, t);
				loadEXP();
				t = lex.nextToken();
				if (t.getTokenType() == TokenType.TERM) {
					armazenaToken(Producoes.ATRIB, t);
				} else {
					throw new ExcecaoSintatica(t.getTokenType());
				}
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadEXP() throws ExcecaoSintatica, IOException {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.LOGIC_VAL) {
			armazenaToken(Producoes.EXP, t);
			loadFVALLOG();
		} else if (t.getTokenType() == TokenType.ID) {
			armazenaToken(Producoes.EXP, t);
			loadFID();
		} else if ((t.getTokenType() == TokenType.NUM_INT) ||
				   (t.getTokenType() == TokenType.NUM_FLOAT)) {
			armazenaToken(Producoes.EXP, t);
			loadFNUM();
		} else if (t.getTokenType() == TokenType.L_PAR) {
			armazenaToken(Producoes.EXP, t);
			loadFLPAR();
		} else if (t.getTokenType() == TokenType.LITERAL) {
			armazenaToken(Producoes.EXP, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}

	}

	public void loadFID() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.LOGIC_OP) {
			armazenaToken(Producoes.FID, t);
			loadFVALLOG();
		} else if ((t.getTokenType() == TokenType.ARIT_AS) ||
				  (t.getTokenType() == TokenType.ARIT_MD)) {
			armazenaToken(Producoes.FID, t);
			loadOPNUM();
			loadFOPNUM();
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadFOPNUM() throws ExcecaoSintatica, IOException {
		Token t = lex.nextToken();
		if ((t.getTokenType() == TokenType.NUM_INT) ||
			(t.getTokenType() == TokenType.NUM_FLOAT)
				|| (t.getTokenType() == TokenType.ID) || (t.getTokenType() == TokenType.L_PAR)) {
			armazenaToken(Producoes.FOPNUM, t);
			loadEXPNUM();
			loadFEXPNUM_1();
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}

	}

	public void loadFEXPNUM_1() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.RELOP) {
			armazenaToken(Producoes.FEXPNUM_1, t);
			loadEXPNUM();
		} else if (t.getTokenType() == TokenType.TERM) {
			armazenaToken(Producoes.FEXPNUM_1, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadFID_1() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.LOGIC_OP) {
			armazenaToken(Producoes.FID_1, t);
			loadFVALLOG();
		} else if (t.getTokenType() == TokenType.RELOP) {
			armazenaToken(Producoes.FID_1, t);
			loadEXPNUM();
		} else if ((t.getTokenType() == TokenType.ARIT_AS) || (t.getTokenType() == TokenType.ARIT_MD)) {
			armazenaToken(Producoes.FID_1, t);
			loadOPNUM();
			loadEXPNUM();
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.RELOP) {
				armazenaToken(Producoes.FID_1, t);
				loadEXPNUM();
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadFNUM() throws ExcecaoSintatica, IOException {
		Token t = lex.nextToken();
		if ((t.getTokenType() == TokenType.ARIT_AS) || (t.getTokenType() == TokenType.ARIT_MD)) {
			armazenaToken(Producoes.FNUM, t);
			loadOPNUM();
			loadFOPNUM();
		} else if (t.getTokenType() == TokenType.TERM) {
			armazenaToken(Producoes.FNUM, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadFLPAR() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if ((t.getTokenType() == TokenType.NUM_INT) || (t.getTokenType() == TokenType.NUM_FLOAT)
				|| (t.getTokenType() == TokenType.ID) || (t.getTokenType() == TokenType.L_PAR)) {
			armazenaToken(Producoes.FLPAR, t);
			loadEXPNUM();
			loadFEXPNUM();
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadFEXPNUM() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.R_PAR) {
			armazenaToken(Producoes.FEXPNUM, t);
			loadFRPAR();
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadFRPAR() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.RELOP) {
			armazenaToken(Producoes.FRPAR, t);
			loadEXPNUM();
		} else if (t.getTokenType() == TokenType.TERM) {
			armazenaToken(Producoes.FRPAR, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadEXPLO() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.LOGIC_VAL) {
			armazenaToken(Producoes.EXPLO, t);
			loadFVALLOG();
		} else if (t.getTokenType() == TokenType.ID) {
			armazenaToken(Producoes.EXPLO, t);
			loadFID_1();
		} else if ((t.getTokenType() == TokenType.NUM_INT) || (t.getTokenType() == TokenType.NUM_FLOAT)) {
			armazenaToken(Producoes.EXPLO, t);
			loadOPNUM();
			loadEXPNUM();
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.RELOP) {
				armazenaToken(Producoes.EXPLO, t);
				loadEXPNUM();
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else if (t.getTokenType() == TokenType.L_PAR) {
			armazenaToken(Producoes.EXPLO, t);
			loadEXPNUM();
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.R_PAR) {
				t = lex.nextToken();
				if (t.getTokenType() == TokenType.RELOP) {
					armazenaToken(Producoes.EXPLO, t);
					loadEXPNUM();
				} else {
					throw new ExcecaoSintatica(t.getTokenType());
				}
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}

	}

	public void loadFVALLOG() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.LOGIC_OP) {
			armazenaToken(Producoes.FVALLOG, t);
			loadEXPLO();
		} else if ((t.getTokenType() == TokenType.R_PAR) || (t.getTokenType() == TokenType.TERM)) {
			armazenaToken(Producoes.FVALLOG, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadEXPNUM() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if ((t.getTokenType() == TokenType.NUM_INT) || (t.getTokenType() == TokenType.NUM_FLOAT)
				|| (t.getTokenType() == TokenType.ID)) {
			armazenaToken(Producoes.EXPNUM, t);
			loadVAL();
			loadXEXPNUM();
		} else if (t.getTokenType() == TokenType.L_PAR) {
			armazenaToken(Producoes.EXPNUM, t);
			loadEXPNUM();
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.R_PAR) {
				armazenaToken(Producoes.EXPNUM, t);
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadXEXPNUM() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if ((t.getTokenType() == TokenType.ARIT_AS) || (t.getTokenType() == TokenType.ARIT_MD)) {
			armazenaToken(Producoes.XEXPNUM, t);
			loadOPNUM();
			loadEXPNUM();
		} else if ((t.getTokenType() == TokenType.ID) || (t.getTokenType() == TokenType.RELOP)
				|| (t.getTokenType() == TokenType.TERM) || (t.getTokenType() == TokenType.R_PAR)
				|| (t.getTokenType() == TokenType.BEGIN) || (t.getTokenType() == TokenType.IF)
				|| (t.getTokenType() == TokenType.FOR) || (t.getTokenType() == TokenType.WHILE)
				|| (t.getTokenType() == TokenType.DECLARE) || (t.getTokenType() == TokenType.TO)) {
			armazenaToken(Producoes.XEXPNUM, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadOPNUM() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if ((t.getTokenType() == TokenType.ARIT_AS) || (t.getTokenType() == TokenType.ARIT_MD)) {
			armazenaToken(Producoes.OPNUM, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadVAL() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if ((t.getTokenType() == TokenType.ID) || (t.getTokenType() == TokenType.NUM_INT)
				|| (t.getTokenType() == TokenType.NUM_FLOAT)) {
			armazenaToken(Producoes.VAL, t);
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadREP() throws IOException, ExcecaoSintatica {
		Token t = lex.nextToken();
		if (t.getTokenType() == TokenType.FOR) {
			armazenaToken(Producoes.REP, t);
			loadREPF();
		} else if (t.getTokenType() == TokenType.WHILE) {
			armazenaToken(Producoes.REP, t);
			loadREPW();
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadREPF() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.FOR) {
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.ID) {
				armazenaToken(Producoes.REPF, t);
				loadEXPNUM();
				t = lex.nextToken();
				if (t.getTokenType() == TokenType.TO) {
					armazenaToken(Producoes.REPF, t);
					loadEXPNUM();
					loadBLOCO();
				} else {
					throw new ExcecaoSintatica(t.getTokenType());
				}
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

	public void loadREPW() throws IOException, ExcecaoSintatica {
		Token t = getStorageToken();
		if (t.getTokenType() == TokenType.WHILE) {
			t = lex.nextToken();
			if (t.getTokenType() == TokenType.L_PAR) {
				armazenaToken(Producoes.REPW, t);
				loadEXPLO();
				t = lex.nextToken();
				if (t.getTokenType() == TokenType.R_PAR) {
					armazenaToken(Producoes.REPW, t);
					loadBLOCO();
				} else {
					throw new ExcecaoSintatica(t.getTokenType());
				}
			} else {
				throw new ExcecaoSintatica(t.getTokenType());
			}
		} else {
			throw new ExcecaoSintatica(t.getTokenType());
		}
	}

}
