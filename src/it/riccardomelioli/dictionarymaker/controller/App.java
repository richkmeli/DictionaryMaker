package it.riccardomelioli.dictionarymaker.controller;

import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import it.riccardomelioli.dictionarymaker.swing.SwingView;
import it.riccardomelioli.dictionarymaker.view.View;

public class App implements Runnable{
	protected View view;	// comunicazione da controller a view
	private int workStatus; // percentuale dove si è arrivati nel cicclo

	@Override
	public void run(){
		workStatus = 0;
		view = new SwingView(this);
	}

	//  scrive sul file di testo tutte le stringhe di lunghezza "length" con i caratteri presenti nel vettore dei simboli
	protected void generate(int keyLength, Vector<Character> symbolVector, PrintWriter txtOut, Boolean chckbxKeyLowerThanLength){
		view.setTxtareaTextBoard("[INITIALIZE] Starting generation of keys");

		int j = keyLength; 	// indice per ciclo for riguardante i le chiavi di lunghezza minore di key
		if (chckbxKeyLowerThanLength == true){
			j = 1; 	// siginifica che vogliamo le chiavi di lunghezza minore
		}

		// calcolo numero chiavi
		int totalKeyNumber = 0;
		for(int k = j; k <= keyLength; ++k){
			totalKeyNumber = totalKeyNumber + (int) Math.pow(symbolVector.size(),k);	//alfabeto ^ lunghezza stringa in quell'iterazione(j), quindi considera anche chiavi lunghezza inferioriori
		}
		int cicleCounter=0; // dentro al ciclo indica quante chiavi sono state generate

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
				++cicleCounter; // piu 1 chiave generata
				workStatus = (int)(((float)cicleCounter)/totalKeyNumber*100);	// percentuale dove si è arrivati
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

		Vector<Character> symbolVector = new Vector<Character>();
		if(chckbx_09 ==  true){
			for(Character c=48;c<=57;++c)
				symbolVector.add((char) c);
		}
		if(chckbx_az ==  true){
			for(Character c=65;c<=90;++c)
				symbolVector.add((char) c);
		}
		if(chckbx_AZ ==  true){
			for(Character c=97;c<=122;++c)
				symbolVector.add((char) c);
		}
		if(chckbx_SpecialCharacter ==  true){
			for(Character c=21;c<=47;++c)
				symbolVector.add((char) c);
			for(Character c=58;c<=64;++c)
				symbolVector.add((char) c);
			for(Character c=91;c<=96;++c)
				symbolVector.add((char) c);
			for(Character c=123;c<=126;++c)
				symbolVector.add((char) c);

		}
		return symbolVector;
	}

	// scrive direttamente sul file di testo, prendendo in input la lunghezza della chiave e le varie possibilita (numerico, alfanumerico, ...)
	public void directTXT(int keyLength,
			boolean chckbx_09,
			boolean chckbx_az,
			boolean chckbx_AZ,
			boolean chckbx_SpecialCharacter,
			boolean chckbxKeyLowerThanLength
			){

		// La generazione delle chiavi è distribuita su un altro thread
		Thread threadGenerate = new Thread(new Runnable() {		// threadGenerate lavora al massimo
			@Override
			public void run() {
				PrintWriter txtOut = openFile("Dictionary.txt");
				generate(keyLength, createSymbolVector(chckbx_09,chckbx_az,chckbx_AZ,chckbx_SpecialCharacter), txtOut, chckbxKeyLowerThanLength);
				txtOut.close();
				view.setTxtareaTextBoard("[FINILAZE] Closing txt stream");
			}
		});
		threadGenerate.start();

		/*
		 * A intervalli costanti viene aggiornata la View
		 * per notificare lo stato di avanzamento nella generazione
		 * delle chiavi
		 */
		Timer updateViewTimer = new Timer();	// timer su cui si puo chiamare uno schedule ogni tot millisecondi, invece che usare un while
		updateViewTimer.schedule(new TimerTask() {
			@Override
			public void run() {

				enableGenerateButton(false);

				if(threadGenerate.isAlive()){	// se il thread è attivo
					updateViewProgressBar(workStatus);
				}
				else {
					updateViewProgressBar(100);
					enableGenerateButton(true);
					this.cancel();
				}
			}
		}, 0,50);	// dalay(tempo che aspetta a partire) = 0 , period(refresh) = 50
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

	// aggiorna progressbar della view
	private void updateViewProgressBar(int perc){
		view.updateProgressBar(perc);
	}

	// abilita disabilita tasto generate della view
	private void enableGenerateButton(boolean val){
		view.enableGenerateButton(val);
	}

	public long numberOfKey(int keyLength,	// TODO : implementa BigInteger
			boolean chckbx_09,
			boolean chckbx_az,
			boolean chckbx_AZ,
			boolean chckbx_SpecialCharacter,
			boolean chckbxKeyLowerThanLength
			){
		Vector<Character> symbolVector = createSymbolVector(chckbx_09,chckbx_az,chckbx_AZ,chckbx_SpecialCharacter);
		long totalKeyNumber = 0;

		int j = keyLength; 	// indice per ciclo for riguardante i le chiavi di lunghezza minore di key
		if (chckbxKeyLowerThanLength == true){
			j = 1; 	// siginifica che vogliamo le chiavi di lunghezza minore
		}
		for(int k = j; k <= keyLength; ++k){
			totalKeyNumber = totalKeyNumber + (int) Math.pow(symbolVector.size(),k);	//alfabeto ^ lunghezza stringa in quell'iterazione(j), quindi considera anche chiavi lunghezza inferioriori
		}
		return totalKeyNumber;
	}
}
