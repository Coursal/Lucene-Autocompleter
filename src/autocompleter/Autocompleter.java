package autocompleter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Autocompleter 
{
    public void get_suggested_words(String input_word) throws IOException
    {
        // Creating the index
        Directory directory = FSDirectory.open(Paths.get("Index"));
        PlainTextDictionary txt_dict = new PlainTextDictionary(Paths.get("eng_dictionary.txt"));
        SpellChecker checker = new SpellChecker(directory);

        System.out.print("\nBuilding index from the .txt dictionary took... ");
        long start_time = System.currentTimeMillis();
            checker.indexDictionary(txt_dict, new IndexWriterConfig(new KeywordAnalyzer()), false);
        long end_time = System.currentTimeMillis();
        System.out.println((end_time - start_time)/1000 + " seconds.");

        
        // Searching and presenting the suggested words
        StandardAnalyzer analyzer = new StandardAnalyzer();
        AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(directory, analyzer);
        
        PlainTextDictionary dictionary = new PlainTextDictionary(Paths.get("eng_dictionary.txt"));
        
        System.out.print("Searching the index for suggestions took... ");
        start_time = System.currentTimeMillis();
            suggester.build(dictionary);
        end_time = System.currentTimeMillis();
        System.out.println((end_time - start_time)/1000 + " seconds.\n");
        
        List<Lookup.LookupResult> suggested_words = suggester.lookup(input_word, 5, true, true);
        
        System.out.println("By '" + input_word + "' did you mean:");
        for(LookupResult suggested_word : suggested_words) 
            System.out.println("\t" + suggested_word.key.toString());
        
        directory.close();
    }
    
    
    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        Autocompleter autocompleter = new Autocompleter();
        
        System.out.println("***Word Autocompleter***");
        System.out.print("Begin to type a word: ");
        String input_word = scan.next();
        
        autocompleter.get_suggested_words(input_word);
    }
}
