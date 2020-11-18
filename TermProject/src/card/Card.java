package card;

public class Card {
	private boolean Showingface=false;
	
	public void setShowingface(boolean showingface) {
		Showingface = showingface;
	}
	
	public boolean isShowingface() {
		return Showingface;
	}

	public void flip() {
		if(Showingface == false)
			Showingface=true;
		else
			Showingface=false;
	}

}
