package com.eni.encheres.dal.retraits;

import com.eni.encheres.bo.Retrait;
import com.eni.encheres.dal.exceptions.RetraitDAOException;

public interface RetraitDAO {
    void insert(Retrait retrait) throws RetraitDAOException;

    void update(Retrait retrait) throws RetraitDAOException;

    Retrait getRetraitByArticleId(int id) throws RetraitDAOException;

    void setArticleIsRetire(int articleId) throws  RetraitDAOException;
}
