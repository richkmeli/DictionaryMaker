package it.riccardomelioli.dictionarymaker;

import java.io.PrintWriter;
import java.util.Vector;

import it.riccardomelioli.dictionarymaker.swing.SwingView;
import it.riccardomelioli.dictionarymaker.view.View;

public class App implements Runnable{
	protected View view;	// comunicazione da controller a view

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		view = new SwingView(this);
	}

	//  scrive sul file di testo tutte le stringhe di lunghezza "length" con i caratteri presenti nel vettore dei simboli
	protected void generate(int keyLength, Vector<Character> symbolVector, PrintWriter txtOut, Boolean chckbxKeyLowerThanLength){
		view.setTxtareaTextBoard("[INITIALIZE] Starting generation of keys");
		
		int j = keyLength; 	// indice per ciclo for riguardante i le chiavi di lunghezza minore di key
		if (chckbxKeyLowerThanLength == true){
			j = 1; 	// siginifica che vogliamo le chiavi di lunghezza minore
		}

		while(j<=keyLength){
			// inizializza la prima tmpKey con il primo valore --- PRIMA
			Vector<Character> tmpKey = new Vector<>();	// si considera j perche la lunghezza della chiave è variabile, quando si considera le chiavi di lungh minore a keyLength
			while(tmpKey.size() < j){
				tmpKey.addElement(symbolVector.get(0));
			}
			
			// crea la chiave a qui dovra fermarsi --- ULTIMA
			Vector<Character> finalKey = new Vector<>();
			while(finalKey.size() < j){
				finalKey.addElement(symbolVector.get(symbolVector.size()-1));
			}
			// stampa caso base
			for(int t=0; t<tmpKey.size(); ++t){
				if(t ==tmpKey.size()-1) txtOut.println(tmpKey.elementAt(t));
				else txtOut.print(tmpKey.elementAt(t));
			}

			// ciclo che crea le chiavi di lunghezza j dalla prima(tmpKey) fino all'ultima(finalKey)
			int tmpKeyCursor=0;
			boolean tmpKeyTerminated = true;
			int cicleCounter=1;
			int[] tmpKeyIndexAlphabet = new int[j];
			
			while(tmpKeyTerminated == true){	//  0037 - 9999 -> end 9999		// NON SI FERMA QUA
					
					tmpKeyCursor = 0;	// rimposti il cursore a sx (inizio)
					
					while(tmpKeyCursor < j){	// modifica indici di tmpKeyIndexAlphabet
						if( tmpKeyIndexAlphabet[tmpKeyCursor] != symbolVector.size()-1) {	// se diverso da ultima lettera alfabeto incrementi
							++tmpKeyIndexAlphabet[tmpKeyCursor];
							break;	// uscita while
						} else {			// se uguale gestisci indici
							tmpKeyIndexAlphabet[tmpKeyCursor] = 0;
							++tmpKeyCursor;
						}
					}
					
					// swap rimuove elemento considerato e mette successivo, in ciclo per la gestione di tutti gli indici insieme
					for(int t=0; t<tmpKey.size(); ++t){
						tmpKey.remove(t);
						tmpKey.insertElementAt(symbolVector.get(tmpKeyIndexAlphabet[t]), t);
					}
					
					// stampa
					for(int t=0; t<tmpKey.size(); ++t){
						if(t ==tmpKey.size()-1) txtOut.println(tmpKey.elementAt(t));
						else txtOut.print(tmpKey.elementAt(t));
					}
					
					// status
					++cicleCounter;
					if( cicleCounter % 10000 == 0 )
						view.setTxtareaTextBoard("[STATUS] keys generated: " + cicleCounter + " key");
				
				// gestione vector matching tra la chiave presa in considerazione e la finale
				tmpKeyTerminated = false; 	// ipotizziamo che abbia finito
				for(int t=0; t<tmpKey.size(); ++t){
					if(tmpKey.elementAt(t) != finalKey.elementAt(t))	// se vi è solo un elemento diverso dalla finale ritorna nel ciclo
						tmpKeyTerminated = true;
				}
			}
			++j;
		}

	}

	// crea il vettore di simboli utilizzati
	public Vector<Character> createSymbolVector(
			boolean chckbx_09,
			boolean chckbx_az,
			boolean chckbx_AZ,
			boolean chckbx_SpecialCharacter
			){
		view.setTxtareaTextBoard("[INITIALIZE] Making vector of alphabet");
		
		Vector<Character> SymbolVector = new Vector<Character>();
		if(chckbx_09 ==  true){
			for(Character c=48;c<=57;++c)
				SymbolVector.add((char) c);
		}
		if(chckbx_az ==  true){
			for(Character c=65;c<=90;++c)
				SymbolVector.add((char) c);
		}
		if(chckbx_AZ ==  true){
			for(Character c=97;c<=122;++c)
				SymbolVector.add((char) c);
		}
		if(chckbx_SpecialCharacter ==  true){
			for(Character c=21;c<=47;++c)
				SymbolVector.add((char) c);
			for(Character c=58;c<=64;++c)
				SymbolVector.add((char) c);
			for(Character c=91;c<=96;++c)
				SymbolVector.add((char) c);
			for(Character c=123;c<=126;++c)
				SymbolVector.add((char) c);

		}
		return SymbolVector;
	}

	// scrive direttamente sul file di testo, prendendo in input la lunghezza della chiave e le varie possibilita (numerico, alfanumerico, ...)
	public void directTXT(int keyLength,
			boolean chckbx_09,
			boolean chckbx_az,
			boolean chckbx_AZ,
			boolean chckbx_SpecialCharacter,
			boolean chckbxKeyLowerThanLength
			){
		PrintWriter txtOut = openFile("Dictionary.txt");
		generate(keyLength, createSymbolVector(chckbx_09,chckbx_az,chckbx_AZ,chckbx_SpecialCharacter), txtOut, chckbxKeyLowerThanLength);
		txtOut.close();
		view.setTxtareaTextBoard("[FINILAZE] Closing txt stream");

	}

	// apre il file di testo su cui scrivere 
	public PrintWriter openFile(String filename){
		view.setTxtareaTextBoard("[INITIALIZE] Opening txt stream");
		PrintWriter txtOut=null;
		try{
			txtOut = new PrintWriter(filename);
		}catch(Throwable throwable){
			throwable.printStackTrace();
		}
		return txtOut;
	}
	
}
