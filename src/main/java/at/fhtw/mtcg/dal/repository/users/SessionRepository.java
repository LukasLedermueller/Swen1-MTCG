package at.fhtw.mtcg.dal.repository.users;

import at.fhtw.mtcg.dal.UnitOfWork;

public class SessionRepository {
    private UnitOfWork unitOfWork;
    public SessionRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }
    //functions
}
