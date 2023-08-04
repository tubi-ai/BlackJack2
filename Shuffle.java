import java.util.Random;
public class Shuffle {
    private String[] deck;
    private int currentCard;
    private int[] suit;
    private int[] face;
    private int value;
    private Random mixCards = new Random();
    private int deckSize = 52;

    public Shuffle(){
        face = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13};
        suit = new int[] {1,2,3,4};

        deck = new String[deckSize];
        currentCard = 0;

        for (int i=0; i<deck.length; i++){
            deck[i] = new String(face[i%13]+""+suit[i/13]);
        }
    }
    public void ShuffleCards(){
        currentCard = 0;
        for (int i=0; i<deck.length;i++){
            int f = mixCards.nextInt(deckSize);

            String temp = deck[i];
            deck[i] = deck[f];
            deck[f] = temp;
        }
    }
    public String Dealer(){
        if(currentCard < deck.length){
            return deck[currentCard++];
        }
        return null;
    }
}
