package HongWu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class ComparatorPokers implements Comparator<Poker> {
    public int compare(Poker p1, Poker p2) {
        //TODO Auto-generated method stub
        if (p1.Rank > p2.Rank)
            return 1;
        else if (p1.Rank == p2.Rank){
            if(p1.Number > p2.Number)
                return 1;
            else if(p1.Number == p2.Number)
                return 0;
            else
                return -1;
        }
        else
            return -1;
    }
}

public class PokerGrant {

    static void poll(PriorityQueue P){
        while (true){
            Poker p = (Poker) P.poll();
            if(p == null) break;
            else PokerGrant.print(p);

        }
        System.out.println();
    }
    static void print(Poker p){
        if(p.Color.equals("red joker") || p.Color.equals("black joker"))
            System.out.println(p.Color);
        else if(p.Number == 11)
            System.out.println(p.Color+"J");
        else if(p.Number == 12)
            System.out.println(p.Color+"Q");
        else if(p.Number == 13)
            System.out.println(p.Color+"K");
        else if(p.Number == 1)
            System.out.println(p.Color+"A");
        else
            System.out.println(p.Color+p.Number);
    }


    public static void main(String[] args){
        List<Poker> pokers = new ArrayList<>();
        PriorityQueue<Poker> game1 = new PriorityQueue<>(new ComparatorPokers());
        PriorityQueue<Poker> game2 = new PriorityQueue<>(new ComparatorPokers());
        PriorityQueue<Poker> game3 = new PriorityQueue<>(new ComparatorPokers());
        PriorityQueue<Poker> game4 = new PriorityQueue<>(new ComparatorPokers());
        PriorityQueue<Poker> last = new PriorityQueue<>(new ComparatorPokers());


        for(int i = 0; i < 108; i++){
            Poker poker = new Poker();
            if(i != 104 && i != 105 && i != 106 &&i != 107)
                poker.Number = i%13+1;
            if(i == 104 || i == 105)
                poker.Color = "red joker";
            else if(i == 106 || i == 107)
                poker.Color = "black joker";
            else if(i/26 == 0)
                poker.Color = "heart";
            else if(i/26 == 1)
                poker.Color = "spade";
            else if(i/26 == 2)
                poker.Color = "club";
            else if(i/26 == 3)
                poker.Color = "diomand";
            pokers.add(poker);
        }

        for(int i = 0; i < 108; i++){
            if(pokers.get(i).Color.equals("heart") && pokers.get(i).Number == 5)
                pokers.get(i).Rank = 8;
            else if(pokers.get(i).Color.equals("red joker"))
                pokers.get(i).Rank = 7;
            else if(pokers.get(i).Color.equals("black joker"))
                pokers.get(i).Rank = 6;
            else if(pokers.get(i).Color.equals("spade") && pokers.get(i).Number == 3)
                pokers.get(i).Rank = 5;
            else if(pokers.get(i).Color.equals("club") && pokers.get(i).Number == 3)
                pokers.get(i).Rank = 4;
            else if(pokers.get(i).Color.equals("spade") && pokers.get(i).Number == 2)
                pokers.get(i).Rank = 3;
            else if( pokers.get(i).Number == 2)
                pokers.get(i).Rank = 2;
            else if(pokers.get(i).Number == 1)
                pokers.get(i).Rank = 1;
            else
                pokers.get(i).Rank = 0;
        }

        Collections.shuffle(pokers);

        for(int i = 0; i < 108; i++){
            if(i/25 == 0)
                game1.add(pokers.get(i));
            else if(i/25 == 1)
                game2.add(pokers.get(i));
            else if(i/25 == 2)
                game3.add(pokers.get(i));
            else if(i/25 == 3)
                game4.add(pokers.get(i));
            else if(i/25 == 4)
                last.add(pokers.get(i));
        }
        System.out.println("Gamer1\n");
        PokerGrant.poll(game1);
        System.out.println("Gamer2\n");
        PokerGrant.poll(game2);
        System.out.println("Gamer3\n");
        PokerGrant.poll(game3);
        System.out.println("Gamer4\n");
        PokerGrant.poll(game4);
        System.out.println("Left\n");
        PokerGrant.poll(last);



    }


}

class Poker{
    String Color;
    //heart 红桃
    //spade 黑桃
    //club 梅花
    //diamond 方块
    //red joker 大王
    //black joker 大王
    int Number;
    int Rank;
}


