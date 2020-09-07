package com.victor.library2.service;

import com.victor.library2.model.Stock;
import com.victor.library2.repository.StockRepository;
import com.victor.library2.repository.StockRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;


    private static final Logger logger = LogManager.getLogger(StockService.class);

    /**
     * Méthode permet de lister toutes les Topos via ce service
     *
     * * @return la liste des topos
     */
    public List<Stock> getAllStocks()
    {
        List<Stock> StockList = stockRepository.findAll();
        logger.debug(StockList.size());
        if(StockList.size() > 0) {
            return StockList;
        } else {
            return new ArrayList<Stock>();
        }
    }

    /**
     * Méthode permet de consulter le Stock en fonction de l'id via ce service
     *
     * @param id
     * * @return le stock via id
     */
    public Stock getStockById(Long id)
    {
        return this.stockRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le stock via ce service
     *
     * @param stock
     * * @return le stock sauvegardé
     */
    public Stock saveStock(Stock stock)
    {
        return this.stockRepository.save(stock);

    }

    /**
     * Méthode permet d'effacer le stock en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteStockById(Long id)
    {
        stockRepository.deleteById(id);
    }


}
