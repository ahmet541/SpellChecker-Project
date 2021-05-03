package Dictionary;

import SpellCorrector.*;
import java.io.IOException;

public class DictionaryTest {
    public static void main(String[] args) throws IOException {
        SpellCorrector spellCorrector = new SpellCorrector();

        // - whereis th elove hehad dated forImuch of thepast who couqdn'tread in sixthgrade and ins pired him
        // + where is the love he had dated for much of the past who couldn't read in sixth grade and inspired him  (9 edits)

        // - in te dhird qarter oflast jear he hadlearned ofa sekretplan
        // + in the third quarter of last year he had learned of a secret plan  (9 edits)

        // - the bigjest playrs in te strogsommer film slatew ith plety of funn
        // + the biggest players in the strong summer film slate with plenty of fun  (9 edits)

        // - Can yu readthis messa ge despite thehorible sppelingmsitakes
        // + can you read this message despite the horrible spelling mistakes  (9 edits)

        System.out.println(spellCorrector.correctAll("whereis th elove hehad dated ForImuch of thepast who couqdn'tread in sixthgrade and ins pired him"));
        
    }
}
