package it.riccardomelioli.dictionarymaker.view;

public interface View {
	public void initialize();
	public void setTxtareaTextBoard(String v);
	public void updateProgressBar(int perc);
	public void enableGenerateButton(boolean val);
	public void refreshTxtNumberIfKey();
}
