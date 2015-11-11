import java.util.HashMap;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;



public class MyEngine implements SearchEngine {
	


    private static final int DEFAULT_SIZE = 5000;

    private int size = 0;
    private int max;
    private boolean breadthFirst = false;

    private HashMap<String, HashSet<String>> indexMap;
    private LinkManager linkManager;



    public MyEngine(){ // DONE
        this(DEFAULT_SIZE);
    }

    public MyEngine(int theMax) { // DONE
        setMax(theMax);
        initialiseVariables();
        createIndex();
        setBreadthFirst();
    }
    
    public void initialiseVariables () {
    	indexMap = new HashMap<String, HashSet<String>>();
    	linkManager = new LinkManager(breadthFirst);
    }

    public void setMax(int theMax){ // DONE
        max = theMax;
    }
    

    
    public boolean setBreadthFirst(){ // TODO
        breadthFirst = true;
    	return true;
       
    }

    public boolean setDepthFirst(){ // TODO
        breadthFirst = false;
    	return true;
    }
    
    
    public void createIndex() {
    	
    	
    	HashSet<String> indexSet = new HashSet<String>();
    	HashSet<String> blackListSet = new HashSet<String>();
    	
    	try {
    		Scanner whiteListScanner = new Scanner(new File("words.txt"));
        	while (whiteListScanner.hasNext()) {
        		indexSet.add(whiteListScanner.next());
        	}
        	whiteListScanner.close();
        	whiteListScanner = null;
        	
    	} catch (FileNotFoundException e) {
    		System.out.println("Caught FileNotFoundException " + e.getMessage());
    	}
    	
    	
    	
    	try {
    		Scanner blackListScanner = new Scanner(new File("stopwords.txt"));
        	while (blackListScanner.hasNext()) {
        		blackListSet.add(blackListScanner.next());
        	}
        	
        	blackListScanner.close();
        	blackListScanner = null;
        	
    	} catch (FileNotFoundException e) {
    		System.out.println("Caught FileNotFoundException " + e.getMessage());
    	}
    
    	
        Iterator<String> blackListIterator = blackListSet.iterator();
        	
        while (blackListIterator.hasNext()) {
       	String blackListWord = blackListIterator.next();
        	for (String whiteListWord : indexSet) {
        		if (whiteListWord.equals(blackListWord)) {
        			indexSet.remove(blackListWord);
        			break;
        		}
        	}
    	}
        
        blackListIterator = null;
        
        blackListSet = null;
        
        for (String finalIndexWord : indexSet) {
        	indexMap.put(finalIndexWord, new HashSet<String>());
        }
        
        indexSet = null;
        
    }

    public void crawlFrom(String webAdress) { // TODO
    	
    	linkManager.addLink(webAdress);
    	
    	
    	WebPageReader reader = new WebPageReader(webAdress);
        reader.run();
        
        linkManager.addLinks(reader.getLinks());
        
        

        /* for (String wordOnWebsite : reader.getWords()) {
        	if (indexMap.keySet().contains(wordOnWebsite.toLowerCase())) {
        		indexMap.get(wordOnWebsite).add(webAdress);
        		size++;
        	}
        } */
        
     for (String wordInIndex : indexMap.keySet()) {
        	for (String wordOnWebsite : reader.getWords()) {
        		if (wordInIndex.equalsIgnoreCase(wordOnWebsite)) {
        			indexMap.get(wordInIndex).add(webAdress);
        			reader.getWords().remove(wordOnWebsite);
        			size++;
        			break;
        		}
        	}
        } 
            
            
        reader = null;
        
        if (size <= max) {    
            this.crawlFrom(linkManager.nextLink());
        } else {
        	linkManager = null;
        	
            Iterator<String>mapIterator = indexMap.keySet().iterator();
            
            while (mapIterator.hasNext()) {
            	String wordToCheck = mapIterator.next();
            	if (indexMap.get(wordToCheck).size() == 0) {
            		mapIterator.remove();
            	}
            	
            }
            
            mapIterator = null;  

        }
    }

    public String[] searchHits(String target){ // TODO
        
    	String[] out = null;
    	
    	
    	if (indexMap.containsKey(target)) {
        	out = indexMap.get(target).toArray(new String[indexMap.get(target).size()]);
        	return out;
    	} else {
    		return new String[0];
    	}
    	 
    	
    }

    public int size(){ // DONE
        return size;
    }

    
    /*
     * Simple test code
     */
    public static void main(String[] args){
        String AFTEN = "https://snl.no";
        String TARGET = "headline";

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
