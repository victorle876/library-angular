package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.Stock;
import com.victor.library2.model.Utilisateur;
import com.victor.library2.service.LivreService;
import com.victor.library2.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/listStock")
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/detailStock/{id}")
    public ResponseEntity<Stock> getlivreById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Stock stockId = stockService.getStockById(id);
        if (stockId == null){
            new ResourceNotFoundException("Stock not found for this id :: " + stockId);
        }
        return ResponseEntity.ok().body(stockId);
    }

    @PostMapping("/addStock")
    public Stock createStock(@RequestBody Stock stock) {
        return this.stockService.saveStock(stock);
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable(value = "id") Long stockId,
                                                @RequestBody Stock stockDetails) throws ResourceNotFoundException {
        Stock stock = stockService.getStockById(stockId);
        if (stockId == null){
                 new ResourceNotFoundException("Stock not found for this id :: " + stockId);
        }
        stock.setId(stockDetails.getId());
        stock.setEtat(stockDetails.getEtat());
        stock.setRayon(stockDetails.getRayon());
        stock.setCommentaire(stockDetails.getCommentaire());
        stock.setNombre(stockDetails.getNombre());
        stock.setIsavailable(stockDetails.getIsavailable());
        final Stock updatedStock = stockService.saveStock(stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/deleteStock/{id}")
    public Map<String, Boolean> deleteStock(@PathVariable(value = "id") Long stockId)
            throws ResourceNotFoundException {
        Stock stock = stockService.getStockById(stockId);
        if (stockId == null){
                 new ResourceNotFoundException("Employee not found for this id :: " + stockId);
        }
        this.stockService.deleteStockById(stockId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
