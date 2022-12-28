package at.fhtw.mtcg.dal.repository.cards;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.model.Card;
import at.fhtw.mtcg.model.CardName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeckRepository {
    private UnitOfWork unitOfWork;
    public DeckRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }


}
