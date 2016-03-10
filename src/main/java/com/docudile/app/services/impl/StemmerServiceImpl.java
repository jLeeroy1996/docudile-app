package com.docudile.app.services.impl;

import com.docudile.app.services.StemmerService;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cicct on 3/10/2016.
 */

@Service("stemmerService")
@Transactional
public class StemmerServiceImpl implements StemmerService{

    @Override
    public boolean checkIfInDictionary(String word) {
        try {
            Dictionary dictionary = Dictionary.getDefaultResourceInstance();
            for (POS pos : POS.getAllPOS()) {
                IndexWord index = dictionary.getIndexWord(pos, word);
                if (index != null) {
                    if (!index.getSenses().isEmpty()) {
                        return true;
                    }
                }
            }
        }  catch (JWNLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
