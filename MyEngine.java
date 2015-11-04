
public class MyEngine implements SearchEngine {

    private static final int DEFAULT_SIZE = 5000;

    private int size = 0;
    private int max;

    public MyEngine(){ // DONE
        this(DEFAULT_SIZE);
    }

    public MyEngine(int theMax) { // DONE
        setMax(theMax);
    }

    public void setMax(int theMax){ // DONE
        max = theMax;
    }
    
    public boolean setBreadthFirst(){ // TODO
        return false;
    }

    public boolean setDepthFirst(){ // TODO
        return false;
    }

    public void crawlFrom(String webAdress){ // TODO
        return;
    }

    public String[] searchHits(String target){ // TODO
        String[] out = new String[0];
        return out;
    }

    public int size(){ // DONE
        return size;
    }

    
    /*
     * Simple test code
     */
    public static void main(String[] args){
        String AFTEN = "https://snl.no";
        String TARGET = "og";

        MyEngine engine = new MyEngine();
        System.out.print("Searching, start....");
        engine.crawlFrom(AFTEN);
        System.out.printf("finish. Size of index = %d%n",engine.size());

        System.out.printf("Occurences of \"%s\":%n",TARGET);
        String[] results = engine.searchHits(TARGET);
        for (String s: results)
            System.out.println(s);
    }
}
