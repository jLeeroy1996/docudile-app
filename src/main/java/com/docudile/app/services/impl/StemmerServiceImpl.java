package com.docudile.app.services.impl;

import com.docudile.app.services.StemmerService;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.stanford.nlp.ling.Word;

/**
 * Created by cicct on 3/10/2016.
 */

@Service("stemmerServiceImpl")
@Transactional
@PropertySource({"classpath:/storage.properties"})
public class StemmerServiceImpl implements StemmerService{

    @Autowired
    private Environment environment;

    @Override
    public boolean checkIfInDictionary(String word) {

        WordNetDatabase database = WordNetDatabase.getFileInstance();

        Synset[] synsets = database.getSynsets(word);
        System.out.println(synsets.length);
        if(synsets.length > 0){
            return false;
        }
        return true;

    }

    @Override
    public void startStemmer() {
        System.setProperty("wordnet.database.dir", environment.getProperty("storage.wordnet"));
    }

}
